function first_push() {
    Platforms.push(new platform(350, 460, 805, 50));
    Platforms.push(new platform(750, 300, 805, 50));
    Coins.push(new coin(650, 425, 30));
    Coins.push(new coin(900, 265, 30));
    Coins.push(new coin(900, 425, 30));
    Coins.push(new coin(1060, 265, 30));
    Coins.push(new coin(1220, 265, 30));
    Obstacles.push(new obstacle(1350, 230, 45, 70));
}

function push_platforms() {
    Platforms.push(new platform(1300, 160, 805, 50));
    Platforms.push(new platform(1350, 440, 805, 50));
    Platforms.push(new platform(2100, 320, 805, 50));
    Platforms.push(new platform(2650, 170, 805, 50));
    Platforms.push(new platform(3450, 300, 805, 50));
    Platforms.push(new platform(4200, 435, 805, 50));
    Platforms.push(new platform(4650, 280, 805, 50));
    Platforms.push(new platform(5000, 570, 805, 50));
    Platforms.push(new platform(5500, 145, 805, 50));
    Platforms.push(new platform(5550, 420, 805, 50));
    Platforms.push(new platform(6300, 285, 805, 50));
    Platforms.push(new platform(6800, 125, 805, 50));
    Platforms.push(new platform(7550, 290, 805, 50));
}

function push_coins() {
    Coins.push(new coin(1400, 125, 30));
    Coins.push(new coin(1700, 125, 30));
    Coins.push(new coin(2000, 125, 30));
    Coins.push(new coin(1550, 405, 30));
    Coins.push(new coin(1750, 405, 30));
    Coins.push(new coin(2550, 285, 30));
    Coins.push(new coin(2750, 135, 30));
    Coins.push(new coin(2900, 135, 30));
    Coins.push(new coin(3050, 135, 30));
    Coins.push(new coin(3200, 135, 30));
    Coins.push(new coin(3600, 265, 30));
    Coins.push(new coin(3800, 265, 30));
    Coins.push(new coin(4000, 265, 30));
    Coins.push(new coin(4300, 400, 30));
    Coins.push(new coin(4500, 400, 30));
    Coins.push(new coin(4800, 245, 30));
    Coins.push(new coin(5000, 245, 30));
    Coins.push(new coin(5200, 245, 30));
    Coins.push(new coin(5100, 535, 30));
    Coins.push(new coin(5250, 535, 30));
    Coins.push(new coin(5400, 535, 30));
    Coins.push(new coin(5550, 535, 30));
    Coins.push(new coin(5700, 535, 30));
    Coins.push(new coin(5900, 385, 30));
    Coins.push(new coin(6100, 385, 30));
    Coins.push(new coin(5800, 110, 30));
    Coins.push(new coin(6000, 110, 30));
    Coins.push(new coin(6550, 250, 30));
    Coins.push(new coin(6750, 250, 30));
    Coins.push(new coin(6950, 250, 30));
    Coins.push(new coin(7200, 90, 30));
    Coins.push(new coin(7700, 255, 30));
    Coins.push(new coin(7900, 255, 30));
    Coins.push(new coin(8100, 255, 30));
}

function push_obstacles() {
    Obstacles.push(new obstacle(2700, 250, 45, 70));
    Obstacles.push(new obstacle(4900, 365, 45, 70));
    Obstacles.push(new obstacle(5650, 350, 45, 70));
}

function push_and_draw() {
    var context = canvas.getContext("2d");
    increase_speed();

    push_platforms();
    push_coins();
    push_obstacles();

    for (let i = 0; i < Platforms.length; i++) {
        Platforms[i].draw(context);
    }
    for (let i = 0; i < Coins.length; i++) {
        Coins[i].draw(context);
    }
    for (let i = 0; i < Obstacles.length; i++) {
        Obstacles[i].draw(context);
    }
}
