#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

typedef struct full_name {
    char name[100];
    char surname[100];
    struct full_name *next;
} full_name;

typedef struct node {
    long long int id;
    char subject[151];
    full_name *name;
    char type[3];
    int time;
    int date;
    struct node *next;
} node;

typedef struct linkedList {
    node *first;
} linkedList;

linkedList deallocate(linkedList list);

node *deallocate_node(node *current);

int check_file(FILE *file) {  //kontrola otvorenia suboru
    if (file == NULL) {
        printf("Zaznamy neboli nacitane!\n");
        return 0;
    }
    return 1;
}

void check_allocation(const char *line) {  //kontrola char dynamickeho pola
    if (line == NULL) {
        printf("Nenacitana pamat.");
        exit(0);
    }
}

void check_node_allocation(const node *node) {  //kontrola nodu
    if (node == NULL) {
        printf("Nenacitana pamat.");
        exit(0);
    }
}

void check_name_allocation(const full_name *name) {  //kontrola full namu
    if (name == NULL) {
        printf("Nenacitana pamat.");
        exit(0);
    }
}

int check_linked_list(linkedList list) { //kontrola linked listu
    if (list.first == NULL) {
        printf("Prazdny zoznam zaznamov\n");
        return 0;
    }
    return 1;
}

char *strip_line(char *line) { //funkcia na odstranenie konca riadku podla posledneho vyskytu znaku
    char *p = strchr(line, '\r'); //prepis \r na \0
    if (p != NULL)
        *p = '\0';
    else {
        p = strchr(line, '\n');  //prepis \n na \0
        if (p != NULL)
            *p = '\0';
    }
    return line;
}

node *create_struct() {
    node *new = (node *) malloc(sizeof(node));
    check_node_allocation(new);
    new->next = NULL;
    return new;
}

full_name *split_names(char *line) {
    int i = 0;
    full_name *first_name, *curr_name = NULL, *prev_name = NULL;
    while ((line != NULL)) {
        prev_name = curr_name;
        curr_name = (full_name *) malloc(sizeof(full_name));
        curr_name->next = NULL; //!!! pridanie NULL
        check_name_allocation(curr_name);
        if (!i) {
            first_name = curr_name;
            i++;
            line = strtok(line, "# ");
        }
        strcpy(curr_name->name, line);
        line = strtok(NULL, "#");
        strcpy(curr_name->surname, line);
		if (prev_name != NULL) {
            prev_name->next = curr_name;
        }
        line = strtok(NULL, "# ");
    }
    return first_name;
}

node *add_data(node *current, int line_number, char *line) {
    full_name *names;
    switch (line_number) {  //rozdelenie vypisu
        case 2:
            current->id = atoi(line);
            break;
        case 3:
            strcpy(current->subject, line);
            break;
        case 4:
            names = split_names(line);
            current->name = names;
            break;
        case 5:
            strcpy(current->type, line);
            break;
        case 6:
            current->time = atoi(line);
            break;
        case 7:
            current->date = atoi(line);
            break;
        default:
            break;
    }
    return current;
}

void add_next_to_prev(node *prev, node *next) {
    if (prev != NULL) {
        prev->next = next;
    }
}

linkedList open_file(linkedList list) {  //n
    node *current = NULL, *prev;  //!!!pridanie NULL 
    FILE *file;
    if (list.first != NULL) {
        deallocate(list);
    }
    file = fopen("OrganizacnePodujatia2.txt", "r");
    if (!check_file(file)) {
        return list;
    }
    int size = 201, line_number = 1, logs = 0, first_node = 1;
    char *line = (char *) malloc(size * sizeof(char));
    check_allocation(line);
    while ((line = fgets(line, size, file)) != NULL) {  //citanie suboru
        if (line[0] == '$') {
            logs++;
        }
        line = strip_line(line);
        if (line_number == 1) {
            prev = current;
            current = create_struct();
            if (first_node) {
                list.first = current;
                first_node--;
            }
            add_next_to_prev(prev, current);
        }
        current = add_data(current, line_number, line);
        line_number++;
        if (line_number == 8) {
            line_number = 1;
        }
    }
    printf("Nacitalo sa %d zaznamov\n", logs);
    free(line);
    fclose(file);
    return list;
}

void print_node(node *current, int data_number) {
    full_name *temp = current->name;
    int author = 1;
    printf("%d.\nID cislo: %lld\n", data_number, current->id);
    printf("Nazov prispevku: %s\n", current->subject);
    printf("Mena autorov:\n");
    while (temp != NULL) {
        printf("\t%d: %s %s\n", author, temp->name, temp->surname);
        temp = temp->next;
        author++;
    }
    printf("Typ prezentovania: %s\n", current->type);
    printf("Cas prezentovania: %d\n", current->time);
    printf("Datum: %d\n", current->date);
}

void print_linked_list(linkedList list) { //v
    node *current = list.first;
    if (!check_linked_list(list)) {
        return;
    }
    int data_number = 1;
    while (current != NULL) {
        print_node(current, data_number);
        data_number++;
        current = current->next;
    }
}

node *fill_struct(node *new) {
    char *subject = (char *) malloc(151 * sizeof(char));
    check_allocation(subject);
    char *name = (char *) malloc(201 * sizeof(char));
    check_allocation(name);
    full_name *authors;
    scanf("%lld", &new->id);
    getchar();
    fgets(subject, 151, stdin);
    subject = strip_line(subject);
    strcpy(new->subject, subject);
    fgets(name, 201, stdin);
    char *name_start = name;
    name = strip_line(name);
    authors = split_names(name);
    new->name = authors;
    scanf("%s", new->type);
    scanf("%d", &new->time);
    scanf("%d", &new->date);
    new->next = NULL;
    free(subject);
    free(name_start);
    return new;
}

linkedList add_node(linkedList list) { //p
    getchar();
    int index, curr_index = 1;
    node *new, *temp = NULL;
    scanf("%d", &index);
    if (index < 1) {
        return list;
    }
    new = create_struct();
    new = fill_struct(new);
    if (list.first == NULL) {
        list.first = new;
        return list;
    }
    node *current = list.first;
    if (index == 1) { //vlozenie na zaciatok
        new->next = current;
        list.first = new;
        return list;
    }
    while (current != NULL) {
        if (temp == NULL && current->next == NULL) { //vlozenie na koniec
            current->next = new;
            return list;
        }
        if (temp == NULL && index - 1 == curr_index && current->next != NULL) {
            temp = current->next;
            current->next = new;
            new->next = temp;
        }
        curr_index++;
        current = current->next;
    }
    return list;
}

char *to_lower(char *line) {
	int i;
    for (i = 0; i < strlen(line); ++i) {
        line[i] = tolower(line[i]);
    }
    return line;
}

char *full_name_to_str(char *name, full_name *author) {
    check_allocation(name);
    strcpy(name, author->name);
    name[strlen(name)] = ' ';
    strcat(name, author->surname);
    name = to_lower(name);
    return name;
}

linkedList remove_node(linkedList list) { //z
    getchar();
    if (!check_linked_list(list)) {
        return list;
    }
    char *name = (char *) malloc(201 * sizeof(char));
    check_allocation(name);
    fgets(name, 201, stdin);
    name = strip_line(name);
    name = to_lower(name); //uprava hladaneho mena na male pismena
    node *current = list.first, *prev = NULL;
    while (current != NULL) {
        int found = 0;
        full_name *author = current->name;
        while (author != NULL) {
            char *name_check = (char *) malloc(201 * sizeof(char)); //string mena autora
            name_check = full_name_to_str(name_check, author); //zmen struct na string na porovnanie
            if (strcmp(name_check, name) == 0) {  //zhoda
                found++;
                break;
            }
            author = author->next;
            free(name_check);
        }
        if (found) {
            printf("Prispevok s názvom %s bol vymazany.\n", current->subject);
            if (current->id != list.first->id) { //zistnie  ci je prvok zaciatok zoznamu
                prev->next = current->next;
                deallocate_node(current);
                current = prev->next;
                continue;
            } else {
                list.first = current->next;
                prev = NULL;
            }
        }
        if (!found) {
            prev = current;
        }
        current = current->next;
    }
    free(name);
    return list;
}

void print_types(linkedList list) { //h
    char type[3];
    int data_number = 0;
    if (!check_linked_list(list)) {
        return;
    }
    scanf("%s", type);
    node *current = list.first;
    while (current != NULL) {
        if ((strcmp(current->type, type) == 0)) {
            data_number++;
            print_node(current, data_number);
        }
        current = current->next;
    }
    if (!data_number) {
        printf("Pre typ: %s nie su ziadne zaznamy.\n", type);
    }
}

linkedList update_type(linkedList list) { //a
    node *current = list.first;
    int id;
    char type[3], old_type[3];
    if (!check_linked_list(list)) {
        return list;
    }
    scanf("%d %s", &id, type);
    while ((id % 15 != 0) || !(strcmp(type, "UP") == 0 || strcmp(type, "UD") == 0 || strcmp(type, "PP") == 0 ||
                               strcmp(type, "PD") == 0)) {
        printf("Zadane udaje nie su korektne, zadaj novy retazec:");
        scanf("%d %s", &id, type);
    }
    while (current != NULL) {
        if (id == current->id) {
            strcpy(old_type, current->type);
            current->id = id;
            strcpy(current->type, type);
            printf("Prispevok s nazvom %s sa bude prezentovat %s [%s].\n", current->subject, current->type, old_type);
        }
        current = current->next;
    }
    return list;
}

void check_prev_first_elem(int index, node **prev) { //kontrola či ma prvok predchodcu
    if (index == 1) {
        *prev = NULL;
    }
}

void set_values(node *current, node **prev, node **curr, node **next, int current_index, int node_index) {
    if (current_index == node_index - 1) {
        if (prev != NULL) {
            *prev = current;
        }
    }
    if (current_index == node_index) {
        *curr = current;
        *next = current->next;
    }
}

linkedList change_first(linkedList list, int index, node *new_first) {
    if (index == 1) {
        list.first = new_first;
    }
    return list;
}

void swap_first_second(int *first, int *second) {
    int temp = *first;
    *first = *second;
    *second = temp;
}

linkedList swap(linkedList list) { //r
    int first, second, current_index = 0;
    node *current, *prev_1, *prev_2, *curr_1 = NULL, *curr_2 = NULL, *next_1, *next_2;
    if (!check_linked_list(list)) {
        return list;
    }
    scanf("%d %d", &first, &second);
    if (first > second) {  //vymena poradia vstupov kvoli spravnemu posunu
        swap_first_second(&first, &second);
    }
    if (first < 1 || second < 1) {
        return list;
    }
    current = list.first;
    check_prev_first_elem(first, &prev_1);
    check_prev_first_elem(first, &prev_2);
    while (current != NULL) {
        current_index++;
        set_values(current, &prev_1, &curr_1, &next_1, current_index, first);
        set_values(current, &prev_2, &curr_2, &next_2, current_index, second);
        current = current->next;
    }
    if (first > current_index || second > current_index) { //privelke cisla
        return list;
    }
    if (curr_1 != NULL && curr_2 != NULL) { //vymena poradia nodov
        if (prev_1 != NULL) {
            prev_1->next = curr_2;
        }
        if (first - second == 1 || second - first == 1) { //podmienky pre dva nody vedla seba
            curr_2->next = curr_1;
        } else {
            curr_2->next = next_1;
            if (prev_2 != NULL && prev_2->next != NULL) {
                prev_2->next = curr_1;
            }
        }
        curr_1->next = next_2;
    }
    list = change_first(list, first, curr_2); //zmena zacaitku zoznamu ak doslo k zmene prveho prvku
    list = change_first(list, second, curr_1);
    return list;
}

node *deallocate_node(node *current) {
    node *next = current->next;
    full_name *curr_name = current->name, *next_name;
    while (curr_name != NULL) {
        next_name = curr_name->next;
        free(curr_name);
        curr_name = next_name;
    }
    current->name = NULL;
    free(current);
    return next;
}

linkedList deallocate(linkedList list) { //k
    node *current = list.first, *next;
    if (!check_linked_list(list)) {
        return list;
    }
    while (current != NULL) {
        next = deallocate_node(current);
        current = next;
    }
    list.first = NULL;
    return list;
}

int main() {
    int input;
    linkedList list;
    list.first = NULL;
    while (1) {
        input = getchar();
        switch (input) {
            case 'n':
                list = open_file(list);
                break;
            case 'v':
                print_linked_list(list);
                break;
            case 'p':
                list = add_node(list);
                break;
            case 'z':
                list = remove_node(list);
                break;
            case 'h':
                print_types(list);
                break;
            case 'a':
                list = update_type(list);
                break;
            case 'r':
                list = swap(list);
                break;
            case 'k':
                list = deallocate(list);
                return 0;
            default:
                continue;
        }
    }
}
