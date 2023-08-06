import re
import sys
import random
import string
import time


class BDDNode:
    def __init__(self, variable, formula):
        self.variable = variable  # A B C None
        self.formula = formula  # A+B.C+D
        self.left = None  # 0
        self.right = None  # 1


class BDD:
    zero_node = BDDNode(None, "0")
    one_node = BDDNode(None, "1")

    def __init__(self, order, variables_amount):
        self.root = None
        self.order = order  # [C, B, A, D]
        self.size = 2
        self.variables_amount = variables_amount


def BDD_create(b_function, order):
    new_bdd = BDD(order, len(order))
    new_bdd.root = BDD_create_rec(new_bdd, b_function, order, 0, {})
    return new_bdd


def BDD_create_rec(bdd, formula, order, index, used_nodes):
    if formula == "1":
        return bdd.one_node
    if formula == "0":
        return bdd.zero_node
    if formula in used_nodes:
        return used_nodes[formula]

    var = order[index]
    pos_formula = formula.replace(var, "1")
    if pos_formula == formula:
        return BDD_create_rec(bdd, formula, order, index + 1, used_nodes)
    pos_formula = simplify_boolean(pos_formula)
    neg_formula = formula.replace(var, "0")
    neg_formula = simplify_boolean(neg_formula)

    bdd.size += 1
    node = BDDNode(var, formula)
    used_nodes[formula] = node
    node.right = BDD_create_rec(bdd, pos_formula, order, index + 1, used_nodes)
    node.left = BDD_create_rec(bdd, neg_formula, order, index + 1, used_nodes)

    return node


def replace_negation(expr):
    expr = expr.replace('!0', '1')
    expr = expr.replace('!1', '0')
    return expr


def simplify_boolean(expr):
    old_expr = expr
    expr = replace_negation(expr)

    patterns = {
        r'[A-Z]\.0': '0',
        r'0\.[A-Z]': '0',
        r'[A-Z]\.1': lambda match: match.group(0)[0],
        r'1\.[A-Z]': lambda match: match.group(0)[2],
        r'[A-Z]\+0': lambda match: match.group(0)[0],
        r'0\+[A-Z]': lambda match: match.group(0)[2],
        r'[A-Z]\+1': '1',
        r'1\+[A-Z]': '1',
        r'0\+0': '0',
        r'0\.0': '0',
        r'1\+1': '1',
        r'1\.1': '1',
        r'0\+1': '1',
        r'1\+0': '1',
        r'0\.1': '0',
        r'1\.0': '0',
    }

    for pattern, replacement in patterns.items():
        expr = re.sub(pattern, replacement, expr)

    if old_expr != expr:
        expr = simplify_boolean(expr)
    return expr


def BDD_create_with_best_order(b_function):
    current_best = None
    current_least = sys.maxsize
    order = sorted(set(c for c in b_function if c.isalpha()))
    for i in range(len(order)):
        created_bdd = BDD_create(b_function, order[:])
        if created_bdd.size < current_least:
            current_best = created_bdd
            current_least = current_best.size
        order = rotate_right(order)
        #random.shuffle(order)
    return current_best


def rotate_right(current_order):
    last_element = current_order.pop()
    current_order.insert(0, last_element)
    return current_order


def BDD_use(bdd, inputs):
    current = bdd.root
    while current.variable is not None and (current.formula != "0" and current.formula != "1"):
        var_index = bdd.order.index(current.variable)
        if inputs[var_index] == 0:
            current = current.left
        else:
            current = current.right
    return current.formula


def test1():
    print("Test for basic functionality:\n")
    process_formula = "A.B+C.D+E.F+G.H"
    bad_order = ["A", "C", "E", "G", "B", "D", "F", "H"]
    some_bdd = BDD_create(process_formula, bad_order)
    values_for_use = [1, 0, 0, 0, 0, 0, 1, 1]
    print("Original formula: " + process_formula)
    print("Not optimal bdd: " + str(some_bdd.size) + " nodes with order " + str(bad_order))
    best_bdd = BDD_create_with_best_order(process_formula)
    print("Least nodes: " + str(best_bdd.size) + " nodes with order: " + str(best_bdd.order))
    print("Boolean value for values " + str(values_for_use) + " : " + BDD_use(best_bdd, values_for_use))


def test2():
    print("\n\nMain test:\n")
    variables = 13
    max_nodes = 2**variables - 1 + 2
    formulas = 100
    start_time = time.time()
    average_reduction = 0
    for i in range(formulas):
        print("Test number " + str(i + 1))
        current_reduction = 0
        generated_formula = generate_formula(variables)
        best_bdd_from_formula = BDD_create_with_best_order(generated_formula)
        values = create_use_values(variables)
        boolean_value = BDD_use(best_bdd_from_formula, values)
        average_reduction += (max_nodes - best_bdd_from_formula.size) / max_nodes * 100
        current_reduction += (max_nodes - best_bdd_from_formula.size) / max_nodes * 100
        print("Formula: " + generated_formula + "\nAmount of variables for the formula: " +
              str(best_bdd_from_formula.variables_amount) + "\nAmount of nodes in the BDD: " +
              str(best_bdd_from_formula.size) + ", the reduction rate is "  + str(current_reduction) +
              " %\nFor values " + str(values) + ",\nassigned to order " +
              str(best_bdd_from_formula.order) + ",\nthe return value is " + boolean_value + "\n")
    average_reduction /= formulas
    print("Average reduction = " + str(average_reduction) + " %")
    print("--- Total time:  %s seconds ---" % (time.time() - start_time))


def generate_formula(amount):
    alphabet = list(string.ascii_uppercase)[0:amount]
    variables_amount = random.randint(amount*2, amount*4)
    formula = random.choice(alphabet)
    operators = ("+", ".")
    negations = ("!", "")
    for i in range(variables_amount):
        formula += random.choice(operators) + random.choice(negations) + random.choice(alphabet)
    missing_elements = set(alphabet) - set(formula)
    for element in missing_elements:
        formula += random.choice(operators) + random.choice(negations) + element
    return formula


def create_use_values(variables_amount):
    list_values = []
    for j in range(variables_amount):
        list_values.append(random.choice([0, 1]))
    return list_values


if __name__ == '__main__':
    test1()
    test2()
