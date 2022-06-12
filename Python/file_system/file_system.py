from typing import Dict, List, Optional, Set, Tuple


class Node:
    def __init__(self, nid: int, name: str, owner: str, is_dir: bool,
                 size: int, parent: Optional["Node"], children: List["Node"]):
        self.nid = nid
        self.name = name
        self.owner = owner
        self.is_dir = is_dir
        self.size = size
        self.parent = parent
        self.children = children

    def find_root(self) -> "Node":
        if self.parent is None:
            return self
        return self.parent.find_root()

    def is_valid(self) -> bool:
        return self.find_root().validation()

    def validation(self) -> bool:
        if self.name == "" and self.parent is not None:
            return False
        if "/" in self.name or self.owner == "":
            return False
        names: List[str] = []
        for child in self.children:
            names.append(child.name)
            if not child.validation():
                return False
        names_set = set(names)
        if len(names_set) != len(names):
            return False
        return True

    def draw(self) -> None:
        last = self.parent is not None
        text = ""
        return self.draw_rec(last, text)

    def draw_rec(self, last: bool, text: str,) -> None:
        if not text:
            text_prefix = prefix = ""
        elif not last:
            text_prefix = prefix = "|"
        else:
            prefix = "\\"
            text_prefix = " "
        print(text + prefix + "-- " + self.name)
        text += text_prefix + "   "
        for index, child in enumerate(self.children):
            child.draw_rec(len(self.children) == index+1, text)

    def full_path(self) -> str:
        path = ""
        path_files: List["Node"] = []
        self.path(path_files)
        path_files.reverse()
        if not path_files:
            return "/"
        for path_file in path_files:
            path += "/" + path_file.name
        if path_files[-1].is_dir:
            path += "/"
        return path

    def path(self, path_files: List["Node"]) -> None:
        if self.parent is None:
            return None
        path_files.append(self)
        self.parent.path(path_files)
        return None

    def disk_usage(self) -> Tuple[int, int]:
        sizes: List[int] = []
        size = 0
        self.count_files(sizes)
        for value in sizes:
            size += value
        return (len(sizes), size)

    def count_files(self, sizes: List[int]) -> None:
        if not self.is_dir:
            sizes.append(self.size)
        for child in self.children:
            child.count_files(sizes)
        return None

    def all_owners(self) -> Set[str]:
        owners: Set[str] = set()
        self.owners_set(owners)
        return owners

    def owners_set(self, owners: Set[str]) -> None:
        owners.add(self.owner)
        for child in self.children:
            child.owners_set(owners)
        return None

    def empty_files(self) -> List['Node']:
        empty: List["Node"] = []
        self.check_empty(empty)
        return empty

    def check_empty(self, empty: List["Node"]) -> None:
        if not self.is_dir and self.size == 0:
            empty.append(self)
        for child in self.children:
            child.check_empty(empty)
        return None

    def prepend_owner_name(self) -> None:
        if not self.is_dir:
            self.name = self.owner + "_" + self.name
        for child in self.children:
            child.prepend_owner_name()
        return None

    def add_keep_files(self, start: int) -> None:
        nids: List[int] = []
        nids.append(start)
        self.new_files(nids)

    def new_files(self, nids: List[int]) -> None:
        if self.is_dir and not self.children:
            new_file = Node(nids[-1], ".keep", self.owner, False, 0, self, [])
            self.children.append(new_file)
            new_nid = nids[-1] + 1
            nids.append(new_nid)
            return None
        for child in self.children:
            child.new_files(nids)
        return None

    def remove_empty_dirs(self) -> None:
        removed_file = self.remove_dirs(False)
        if removed_file:
            self.remove_empty_dirs()

    def remove_dirs(self, removed_file: bool) -> bool:
        new_children = []
        for child in self.children:
            if not child.is_dir or child.children:
                new_children.append(child)
            else:
                removed_file = True
        self.children = new_children
        for child in self.children:
            removed_file = child.remove_dirs(removed_file)
        return removed_file

    def remove_all_foreign(self, user: str) -> None:
        new_children = []
        for child in self.children:
            if child.owner == user:
                new_children.append(child)
        self.children = new_children
        for child in self.children:
            child.remove_all_foreign(user)


def build_fs(metadata: Dict[int, Tuple[str, str]],
             file_sizes: Dict[int, int],
             dir_content: Dict[int, List[int]]) -> Optional[Node]:
    all_nodes: List["Node"] = []
    root_count = 0
    if wrong_file_sizes(metadata, file_sizes):
        return None
    build(metadata, all_nodes)
    for node in all_nodes:
        if node.nid in file_sizes.keys():
            node.size = file_sizes[node.nid]
            node.is_dir = False
        if not change_parent_children(node, metadata, dir_content, all_nodes):
            return None
        if node.parent is None:
            root = node
            root_count += 1
    if root_count != 1:
        return None
    return root


def wrong_file_sizes(metadata: Dict[int, Tuple[str, str]],
                     file_sizes: Dict[int, int]) -> bool:
    for element in file_sizes.keys():
        if element not in metadata.keys():
            return True
    return False


def build(metadata: Dict[int, Tuple[str, str]],
          all_nodes: List["Node"]) -> None:
    for nid in metadata.keys():
        node = Node(nid, metadata[nid][0], metadata[nid][1], True, 0, None, [])
        all_nodes.append(node)


def change_parent_children(node: "Node", metadata: Dict[int, Tuple[str, str]],
                           dir_content: Dict[int, List[int]],
                           all_nodes: List["Node"]) -> bool:
    nid = node.nid
    for key, values in dir_content.items():
        if not in_metadata(key, values, metadata):
            return False
        if nid == key:
            if not node.is_dir:
                return False
            modify_children(node, dir_content, all_nodes)
        if nid in values:
            for my_node in all_nodes:
                if my_node.nid == key:
                    node.parent = my_node
    return True


def in_metadata(key: int, values: List[int],
                metadata: Dict[int, Tuple[str, str]]) -> bool:
    if key not in metadata.keys():
        return False
    for value in values:
        if value not in metadata.keys():
            return False
    return True


def modify_children(node: "Node", dir_content: Dict[int, List[int]],
                    all_nodes: List["Node"]) -> None:
    for child in dir_content[node.nid]:
        for node_from_all in all_nodes:
            if child == node_from_all.nid:
                node.children.append(node_from_all)

# END OF MY IMPLEMENTATION, THE TESTS BELOW WERE PROVIDED BY MY UNIVERSITY


def test_root_only() -> None:
    root = build_fs({1: ("", "root")}, {}, {})
    assert root is not None
    assert root.nid == 1
    assert root.name == ""
    assert root.owner == "root"
    assert root.is_dir
    assert root.size == 0
    assert root.parent is None
    assert root.children == []
    assert root.is_valid()
    assert root.full_path() == "/"
    assert root.disk_usage() == (0, 0)
    assert root.all_owners() == {"root"}
    assert root.empty_files() == []


def test_example() -> None:
    root = example_fs()
    assert root is not None
    assert root.name == 'MY_FS'
    assert root.owner == 'root'
    home = root.children[2]
    assert home.name == 'home'
    assert home.owner == 'root'
    ib111 = home.children[0].children[0]
    assert ib111.name == 'ib111'
    assert ib111.owner == 'user'
    assert ib111.is_dir

    assert len(ib111.children[0].children) == 4
    python = root.children[0].children[1]
    assert python.name == 'python'
    assert python.owner == 'root'
    assert python.size == 14088
    assert not python.is_dir
    assert root.children[3].is_dir

    assert ib111.parent is not None
    assert ib111.parent.parent == home

    assert ib111.is_valid()
    assert python.is_valid()
    python.name = ""
    assert not ib111.is_valid()
    python.name = "python"

    assert python.full_path() == '/bin/python'
    assert ib111.children[0].full_path() == '/home/user/ib111/reviews/'

    assert root.disk_usage() == (8, 1210022)
    assert home.disk_usage() == (4, 78326)

    assert root.all_owners() == {'nobody', 'user', 'root'}
    assert home.all_owners() == {'user', 'root'}
    assert python.all_owners() == {'root'}

    empty = ib111.children[0].children[3]
    assert empty.name == '.timestamp'
    assert root.empty_files() == [empty]

    root.prepend_owner_name()
    assert python.name == 'root_python'
    assert empty.name == 'user_.timestamp'

    root.add_keep_files(7000)

    keep1 = root.children[-1].children[0]
    assert keep1.name == '.keep'
    assert keep1.size == 0
    assert not keep1.is_dir

    keep2 = home.children[0].children[1].children[0].children[0]
    assert keep2.name == '.keep'
    assert keep2.size == 0
    assert not keep2.is_dir

    empty_files = root.empty_files()
    assert len(empty_files) == 3
    assert empty in empty_files
    assert keep1 in empty_files
    assert keep2 in empty_files
    assert keep1.nid + keep2.nid == 7000 + 7001


def draw_example() -> None:
    root = example_fs()
    print("První příklad:")
    root.draw()
    print("\nDruhý příklad:")
    root.children[2].draw()

    print("\nPrvní příklad, po použití root.remove_empty_dirs():")
    root = example_fs()
    root.remove_empty_dirs()
    root.draw()

    print("\nPrvní příklad, po použití root.remove_all_foreign('root'):")
    root = example_fs()
    root.remove_all_foreign('root')
    root.draw()

    print("\nPrvní příklad, po použití root.remove_all_foreign('nobody'):")
    root = example_fs()
    root.remove_all_foreign('nobody')
    root.draw()


def example_fs() -> Node:
    root = build_fs(
        {
            1: ("MY_FS", "root"),
            17: ("bash", "root"),
            42: ("bin", "root"),
            9: ("ls", "root"),
            11: ("python", "root"),
            20: ("usr", "root"),
            1007: ("bin", "root"),
            1100: ("env", "root"),
            999: ("home", "root"),
            2001: ("ib111", "user"),
            25: ("user", "user"),
            2002: ("reviews", "user"),
            3000: ("review1.txt", "user"),
            3017: ("review2.txt", "user"),
            3005: ("review3.txt", "user"),
            100: ("tmp", "nobody"),
            2003: ("pv264", "user"),
            3001: ("projects", "user"),
            1234: (".timestamp", "user"),
        },
        {
            9: 141936,
            11: 14088,
            1100: 47656,
            17: 928016,
            3000: 11660,
            3017: 12345,
            3005: 54321,
            1234: 0,
        },
        {
            1: [42, 20, 999, 100],
            42: [9, 11, 17],
            20: [1007],
            1007: [1100],
            999: [25],
            25: [2001, 2003],
            2001: [2002],
            2002: [3000, 3017, 3005, 1234],
            2003: [3001],
        })
    assert root is not None
    return root


if __name__ == '__main__':
    test_root_only()
    test_example()
    draw_example()
