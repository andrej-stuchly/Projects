from typing import List, Set, Dict, Optional


class Transaction:
    def __init__(self, buyer_id: str, seller_id: str, amount: int, price: int):
        self.buyer_id = buyer_id
        self.seller_id = seller_id
        self.amount = amount
        self.price = price


class Order:
    def __init__(self, trader_id: str, amount: int, price: int):
        self.trader_id = trader_id
        self.amount = amount
        self.price = price


class Stock:
    def __init__(self) -> None:
        self.history: List[Transaction] = []
        self.buyers: List[Order] = []
        self.sellers: List[Order] = []

    def add_trader(self, order: Order, buyer: bool) -> None:
        if buyer:
            for index, element in enumerate(self.buyers):
                if element.price >= order.price:
                    self.buyers.insert(index, order)
                    return None
            self.buyers.append(order)
        else:
            for index, element in enumerate(self.sellers):
                if element.price <= order.price:
                    self.sellers.insert(index, order)
                    return None
            self.sellers.append(order)

    def get_buyers(self) -> List[Order]:
        return self.buyers

    def delete_buyers(self) -> None:
        self.buyers.pop()

    def get_sellers(self) -> List[Order]:
        return self.sellers

    def delete_sellers(self) -> None:
        self.sellers.pop()

    def get_history(self) -> List[Transaction]:
        return self.history

    def add_history(self, transfer: Transaction) -> None:
        self.history.append(transfer)


StockExchange = Dict[str, Stock]


def add_new_stock(stock_exchange: StockExchange, ticker_symbol: str) -> bool:
    if ticker_symbol in stock_exchange.keys():
        return False
    stock_exchange[ticker_symbol] = Stock()
    return True


def place_buy_order(stock_exchange: StockExchange, ticker_symbol: str,
                    trader_id: str, amount: int, price: int) -> None:
    buy = True
    place_order(stock_exchange, ticker_symbol, trader_id, amount, price, buy)


def place_sell_order(stock_exchange: StockExchange, ticker_symbol: str,
                     trader_id: str, amount: int, price: int) -> None:
    buy = False
    place_order(stock_exchange, ticker_symbol, trader_id, amount, price, buy)


def place_order(stock_exchange: StockExchange, ticker_symbol: str,
                trader_id: str, amount: int, price: int, buy: bool) -> None:
    order = Order(trader_id, amount, price)
    stocks = stock_exchange[ticker_symbol]
    if buy:
        stocks.add_trader(order, True)
        seller = stocks.get_sellers()
        if not seller:
            return None
        order_price = seller[-1].price
        order_amount = seller[-1].amount
        order_id = seller[-1].trader_id
    else:
        stocks.add_trader(order, False)
        buyer = stocks.get_buyers()
        if not buyer:
            return None
        order_price = buyer[-1].price
        order_amount = buyer[-1].amount
        order_id = buyer[-1].trader_id
    if order_price > price and buy:
        return None
    elif order_price < price and not buy:
        return None
    if order_amount - amount > 0:
        if buy:
            transfer = Transaction(trader_id, order_id, amount, order_price)
            stocks.sellers[-1].amount -= amount
            stocks.delete_buyers()
        else:
            transfer = Transaction(order_id, trader_id, amount, price)
            stocks.buyers[-1].amount -= amount
            stocks.delete_sellers()
        stocks.add_history(transfer)
    elif order_amount - amount == 0:
        if buy:
            transfer = Transaction(trader_id, order_id, amount, order_price)
        else:
            transfer = Transaction(order_id, trader_id, amount, price)
        stocks.delete_buyers()
        stocks.delete_sellers()
        stocks.add_history(transfer)
    else:
        if buy:
            transfer = Transaction(trader_id, order_id, order_amount,
                                   order_price)
        else:
            transfer = Transaction(order_id, trader_id, order_amount, price)
        stocks.add_history(transfer)
        stocks.delete_buyers()
        stocks.delete_sellers()
        if buy:
            place_buy_order(stock_exchange, ticker_symbol, trader_id,
                            amount-order_amount, price)
        else:
            place_sell_order(stock_exchange, ticker_symbol, trader_id,
                             amount-order_amount, price)


def stock_owned(stock_exchange: StockExchange, trader_id: str) \
        -> Dict[str, int]:
    stock_dict: Dict[str, int] = {}
    for key, value in stock_exchange.items():
        total_amount = 0
        history = value.get_history()
        for element in history:
            if element.buyer_id == trader_id:
                total_amount += element.amount
            if element.seller_id == trader_id:
                total_amount -= element.amount
            if total_amount != 0:
                stock_dict[key] = total_amount
    return stock_dict


def all_traders(stock_exchange: StockExchange) -> Set[str]:
    traders = history_traders(stock_exchange).union(buyer_seller_traders
                                                    (stock_exchange))
    return traders


def history_traders(stock_exchange: StockExchange) -> Set[str]:
    traders = set()
    for key, value in stock_exchange.items():
        history = value.get_history()
        for element in history:
            traders.add(element.buyer_id)
            traders.add(element.seller_id)
    return traders


def buyer_seller_traders(stock_exchange: StockExchange) -> Set[str]:
    traders = set()
    for key, value in stock_exchange.items():
        buyers = value.get_buyers()
        sellers = value.get_sellers()
        for element in buyers:
            traders.add(element.trader_id)
        for element in sellers:
            traders.add(element.trader_id)
    return traders


def transactions_by_amount(stock_exchange: StockExchange,
                           ticker_symbol: str) -> List[Transaction]:
    if ticker_symbol in stock_exchange:
        history = stock_exchange[ticker_symbol].get_history()
        history.sort(key=lambda tup: tup.amount, reverse=True)
    return history


def process_batch_commands(stock_exchange: StockExchange,
                           commands: List[str]) -> Optional[int]:
    for index, command in enumerate(commands):
        dot_split = command.split(":")
        command_list = command.split()
        okay = False
        is_space = False
        for i, part in enumerate(command_list):
            if part == ":" and i == 0:
                return index
            if part == "ADD":
                if len(command_list) != 2:
                    return 0
                for letter in command_list[i+1]:
                    if letter.isspace():
                        is_space = True
                if not is_space:
                    add_new_stock(stock_exchange, command_list[1])
                    okay = True
            elif part == "SELL" or part == "BUY":
                spaces = dot_split[1].count(" ")
                if spaces != len(dot_split[1].split()):
                    return index
                name = " ".join(command_list[:i])
                doubledot = name.count(":")
                if doubledot != 1:
                    return index
                name = name.replace(":", "")
                amount = int(command_list[i+1])
                ticker = command_list[i+2]
                if not(ticker.isalpha()):
                    return index
                price = int(command_list[i+4])
                if part == "SELL":
                    place_sell_order(stock_exchange, ticker,
                                     name, amount, price)
                else:
                    place_buy_order(stock_exchange, ticker,
                                    name, amount, price)
                okay = True
        if not okay:
            return index
    return None

# END OF MY IMPLEMENTATION, THE TESTS BELOW WERE PROVIDED BY MY UNIVERSITY


def print_stock(stock_exchange: StockExchange, ticker_symbol: str) -> None:
    assert ticker_symbol in stock_exchange

    stock = stock_exchange[ticker_symbol]
    print(f"=== {ticker_symbol} ===")
    print("     price amount  trader")
    print("  -------------------------------------------------------------")

    for order in stock.sellers:
        print(f"    {order.price:6d} {order.amount:6d} ({order.trader_id})")
    print("  -------------------------------------------------------------")

    for order in reversed(stock.buyers):
        print(f"    {order.price:6d} {order.amount:6d} ({order.trader_id})")
    print("  -------------------------------------------------------------")

    for transaction in stock.history:
        print(f"    {transaction.seller_id} -> {transaction.buyer_id}: "
              f"{transaction.amount} at {transaction.price}")


def check_order(order: Order, trader_id: str, amount: int, price: int) -> None:
    assert order.trader_id == trader_id
    assert order.amount == amount
    assert order.price == price


def check_transaction(transaction: Transaction, buyer_id: str, seller_id: str,
                      amount: int, price: int) -> None:
    assert transaction.buyer_id == buyer_id
    assert transaction.seller_id == seller_id
    assert transaction.amount == amount
    assert transaction.price == price


def test_scenario1() -> None:
    duckburg_se: StockExchange = {}
    add_new_stock(duckburg_se, 'ACME')

    place_sell_order(duckburg_se, 'ACME', 'Strýček Skrblík', 50, 120)
    place_buy_order(duckburg_se, 'ACME', 'Rampa McKvák', 100, 90)
    place_sell_order(duckburg_se, 'ACME', 'Hamoun Držgrešle', 70, 110)
    place_sell_order(duckburg_se, 'ACME', 'Kačer Donald', 20, 120)

    acme = duckburg_se['ACME']
    assert acme.history == []

    assert len(acme.buyers) == 1
    check_order(acme.buyers[0], 'Rampa McKvák', 100, 90)

    assert len(acme.sellers) == 3
    check_order(acme.sellers[0], 'Kačer Donald', 20, 120)
    check_order(acme.sellers[1], 'Strýček Skrblík', 50, 120)
    check_order(acme.sellers[2], 'Hamoun Držgrešle', 70, 110)

    place_buy_order(duckburg_se, 'ACME', 'Paní Čvachtová', 90, 110)

    assert len(acme.history) == 1
    check_transaction(acme.history[0], 'Paní Čvachtová', 'Hamoun Držgrešle',
                      70, 110)

    assert len(acme.buyers) == 2
    check_order(acme.buyers[0], 'Rampa McKvák', 100, 90)
    check_order(acme.buyers[1], 'Paní Čvachtová', 20, 110)

    assert len(acme.sellers) == 2
    check_order(acme.sellers[0], 'Kačer Donald', 20, 120)
    check_order(acme.sellers[1], 'Strýček Skrblík', 50, 120)

    print_stock(duckburg_se, "ACME")
    
    place_buy_order(duckburg_se, 'ACME', 'Magika von Čáry', 60, 130)
    
    print("\nChanges after adding a buy order for 60 stocks each at price 130\n")
    print_stock(duckburg_se, "ACME")

    assert len(acme.history) == 3
    check_transaction(acme.history[0], 'Paní Čvachtová', 'Hamoun Držgrešle',
                      70, 110)
    check_transaction(acme.history[1], 'Magika von Čáry', 'Strýček Skrblík',
                      50, 120)
    check_transaction(acme.history[2], 'Magika von Čáry', 'Kačer Donald',
                      10, 120)

    assert len(acme.buyers) == 2
    check_order(acme.buyers[0], 'Rampa McKvák', 100, 90)
    check_order(acme.buyers[1], 'Paní Čvachtová', 20, 110)

    assert len(acme.sellers) == 1
    check_order(acme.sellers[0], 'Kačer Donald', 10, 120)

    for name, amount in [
            ('Kačer Donald', -10),
            ('Strýček Skrblík', -50),
            ('Hamoun Držgrešle', -70),
            ('Paní Čvachtová', 70),
            ('Magika von Čáry', 60),
    ]:
        assert stock_owned(duckburg_se, name) == {'ACME': amount}
    
    assert stock_owned(duckburg_se, 'Rampa McKvák') == {}
    assert stock_owned(duckburg_se, 'Šikula') == {}

    assert all_traders(duckburg_se) == {
        'Kačer Donald',
        'Strýček Skrblík',
        'Hamoun Držgrešle',
        'Paní Čvachtová',
        'Magika von Čáry',
        'Rampa McKvák',
    }

    all_transactions = transactions_by_amount(duckburg_se, 'ACME')
    check_transaction(all_transactions[0],
                      'Paní Čvachtová', 'Hamoun Držgrešle',
                      70, 110)
    check_transaction(all_transactions[1],
                      'Magika von Čáry', 'Strýček Skrblík',
                      50, 120)
    check_transaction(all_transactions[2],
                      'Magika von Čáry', 'Kačer Donald',
                      10, 120)


def test_scenario2() -> None:
    duckburg_se: StockExchange = {}
    result = process_batch_commands(duckburg_se, [
        "ADD ACME",
        "Uncle Scrooge: SELL 50 ACME AT 120",
        "Launchpad McQuack: BUY 100 ACME AT 90",
        "Flintheart Glomgold: SELL 70 ACME AT 110",
        "Donald Duck: SELL 20 ACME AT 120",
        "Mrs. Beakley: BUY 90 ACME AT 110",
        "Magica De Spell: BUY 60 ACME AT 130",
    ])
    assert result is None
    assert 'ACME' in duckburg_se
    acme = duckburg_se['ACME']

    assert len(acme.history) == 3
    check_transaction(acme.history[0], 'Mrs. Beakley', 'Flintheart Glomgold',
                      70, 110)
    check_transaction(acme.history[1], 'Magica De Spell', 'Uncle Scrooge',
                      50, 120)
    check_transaction(acme.history[2], 'Magica De Spell', 'Donald Duck',
                      10, 120)

    assert len(acme.buyers) == 2
    check_order(acme.buyers[0], 'Launchpad McQuack', 100, 90)
    check_order(acme.buyers[1], 'Mrs. Beakley', 20, 110)

    assert len(acme.sellers) == 1
    check_order(acme.sellers[0], 'Donald Duck', 10, 120)


def test_scenario3() -> None:
    nnyse: StockExchange = {}
    result = process_batch_commands(nnyse, [
        "ADD Momcorp",
        "Mom: SELL 1000 Momcorp AT 5000",
        "Walt: BUY 10 Momcorp AT 5600",
        "Larry: BUY 7 Momcorp AT 5000",
        "Igner: BUY 1 Momcorp AT 4000",
        "ADD PlanetExpress",
        "Mom: BUY 1000 PlanetExpress AT 100",
        "Zoidberg: BUY 1000 PlanetExpress AT 199",
        "Professor Farnsworth: SELL 1020 PlanetExpress AT 100",
        "Bender B. Rodriguez: BUY 20 Momcorp AT 100",
        "Fry: INVALID COMMAND",
        "Leela: BUY 500 PlanetExpress AT 150",
    ])

    assert result == 10

    assert set(nnyse) == {'Momcorp', 'PlanetExpress'}

    momcorp = nnyse['Momcorp']
    pe = nnyse['PlanetExpress']

    assert len(momcorp.history) == 2
    check_transaction(momcorp.history[0], 'Walt', 'Mom', 10, 5000)
    check_transaction(momcorp.history[1], 'Larry', 'Mom', 7, 5000)

    assert len(momcorp.sellers) == 1
    check_order(momcorp.sellers[0], 'Mom', 983, 5000)

    assert len(momcorp.buyers) == 2
    check_order(momcorp.buyers[0], 'Bender B. Rodriguez', 20, 100)
    check_order(momcorp.buyers[1], 'Igner', 1, 4000)

    assert len(pe.history) == 2
    check_transaction(pe.history[0], 'Zoidberg', 'Professor Farnsworth',
                      1000, 100)
    check_transaction(pe.history[1], 'Mom', 'Professor Farnsworth',
                      20, 100)

    assert pe.sellers == []
    assert len(pe.buyers) == 1
    check_order(pe.buyers[0], 'Mom', 980, 100)

    se: StockExchange = {}
    add_new_stock(se, 'ACME')
    assert process_batch_commands(se, ['ADD: keke']) == 0
    assert process_batch_commands(se, ['ABA: SELL 10 ACME AT 12']) is None
    assert process_batch_commands(se, ['ABA:: SELL 10 ACME AT 12']) == 0
    assert process_batch_commands(se, ['A:BA: SELL 10 ACME AT 12']) == 0
    assert process_batch_commands(se, [': BUY 10 ACME AT 123']) == 0
    assert process_batch_commands(se, [": BUY 15 ACME AT  20"]) == 0
    assert process_batch_commands(se, ['ADD ab c']) == 0
    assert process_batch_commands(se, ['Mike Jason:BUY 100 ACME AT 20']) == 0
    assert process_batch_commands(se, ['asdf: BUY 123    ACME    AT 456']) == 0
    assert process_batch_commands(se, ['asdf:BUY 123 ACME AT 456']) == 0
    assert process_batch_commands(
        se, ['asdf    : BUY 123 ACME AT 456']) is None


if __name__ == '__main__':
    test_scenario1()
    test_scenario2()
    test_scenario3()
