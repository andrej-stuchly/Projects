var score = 0, speed = 3, gravity = 5, jump_speed = 20, platforms = 0, jumps, falls, jumps_amount = 10,
    falls_amount = 5, color_change = 0;
var jump = false, jumping_down = false, platform_collision = false, end = false, red = true;

var coin_audio = new Audio('./audio/coin.ogg');

document.addEventListener('keydown', keyDownHandler);
document.addEventListener('keyup', keyUpHandler);


let Player = new player(400, 185, 45, 70);
const Platforms = [];
const Coins = [];
const Obstacles = [];


function increase_speed() {
    speed += 2;
}

function game_start() {
    var canvas = document.getElementById("canvas");
    var context = canvas.getContext("2d");

    score = 0;
    context.clearRect(0, 0, 1450, 650);
    Player.draw(context);

    first_push();
    push_and_draw();

    game();
}

function jumping() {
    var context = canvas.getContext("2d");
    if (jump && platform_collision) {
        jumps = jumps_amount;
    } else if (jumping_down && platform_collision) {
        falls = falls_amount;
    }
    if (jumps) {
        Player.jump_up(context);
        jumps--;
    } else if (falls) {
        Player.jump_down(context);
        falls--;
    }

    Player.draw(context);
}

function game() {
    var context = canvas.getContext("2d");

    Player.y += gravity;
    jumping();

    if (!jumping_down) {
        for (let i = 0; i < Platforms.length; i++) {
            Platforms[i].collision();
        }
    }
    if (platforms) {
        platform_collision = true;
        platforms = 0;
    } else {
        platform_collision = false;
    }

    for (let i = 0; i < Platforms.length; i++) {
        Platforms[i].move(context);
    }
    for (let i = 0; i < Coins.length; i++) {
        Coins[i].move(context);
        Coins[i].collision(context);
    }
    for (let i = 0; i < Obstacles.length; i++) {
        Obstacles[i].move(context);
        Obstacles[i].collision();
    }

    if (Platforms[Platforms.length - 2].x + 200 < 0) {
        push_and_draw();
    }

    draw_score(context);

    if (Player.y + Player.height > 649 || end) {
        game_over();
    } else {
        window.requestAnimationFrame(game);
    }
}

function draw_score(context) {
    context.clearRect(800, 25, 400, 25);
    context.fillStyle = "white";
    context.font = "30px Calibri";
    context.fillText("Score : " + score, 1000, 50);
}

function stop_audio() {
    coin_audio.pause();
    coin_audio.currentTime = 0;
}

//ovladanie

function keyDownHandler(event) {
    if (event.keyCode == 87) {
        jump = true;
    } else if (event.keyCode == 83 && platform_collision) {
        jumping_down = true;
    }
}

function keyUpHandler(event) {
    if (event.keyCode == 87) {
        jump = false;
    } else if (event.keyCode == 83) {
        jumping_down = false;
    }
}
