function start() {
    document.getElementById("game_id").className = "game active";
    document.getElementById("menu_id").className = "menu passive";
    game_start();
}

function rules() {
    document.getElementById("rules_id").className = "rules active";
    document.getElementById("menu_id").className = "menu passive";
}

function exit() {
    document.getElementById("menu_id").className = "menu passive";
    document.getElementById("game_over_id").className = "game_over passive";
    if (playing) {
        var menu_music = document.getElementById("menu_audio");
        menu_music.pause();
    }
}

function menu() {
    document.getElementById("menu_id").className = "menu active";
    document.getElementById("rules_id").className = "rules passive";
    document.getElementById("game_over_id").className = "game_over passive";
}

function game_over() {
    var game_over_audio = new Audio('./audio/game_over.ogg');
    game_over_audio.play();
    Platforms.length = 0;
    Coins.length = 0;
    Obstacles.length = 0;
    Player.x = 400;
    Player.y = 185;
    speed = 3;
    end = false;
    document.getElementById('output').innerHTML = "Your score: " + score;
    document.getElementById("game_over_id").className = "game_over active";
    document.getElementById("game_id").className = "game passive";
}

var playing = true;

function play_audio() {
    var menu_music = document.getElementById("menu_audio");
    var audio_button_menu = document.getElementById("audio_button_menu");
    var audio_button_rules = document.getElementById("audio_button_rules");
    if (playing) {
        menu_music.pause();
        playing = false;
        audio_button_menu.textContent = "Play Audio";
        audio_button_rules.textContent = "Play Audio";
    } else {
        menu_music.play();
        playing = true;
        audio_button_menu.textContent = "Stop Audio";
        audio_button_rules.textContent = "Stop Audio";
    }
}