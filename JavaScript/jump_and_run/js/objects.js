class player {
    constructor(x, y, width, height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    draw(context) {
        context.clearRect(this.x, this.y - gravity, this.width, this.height);
        context.clearRect(this.x, this.y - 30, 55, 55);
        context.beginPath();
        context.fillStyle = 'blue';
        context.fillRect(this.x, this.y, this.width, this.height);
        context.arc(this.x + 22.5, this.y, 20, 0, 2 * Math.PI);
        context.fillStyle = 'antiquewhite';
        context.fill();
        context.stroke();
    }

    jump_up(context) {
        context.clearRect(this.x, this.y + gravity, this.width, this.height);
        context.clearRect(this.x, this.y - 30, 55, 55);
        this.y -= jump_speed;
    }

    jump_down(context) {
        context.clearRect(this.x, this.y - gravity, this.width, this.height);
        context.clearRect(this.x, this.y - 30, 55, 55);
        this.y += jump_speed;
    }
}

class platform {
    constructor(x, y, width, height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    draw(context) {
        context.beginPath();
        context.fillStyle = 'mediumseagreen';
        context.fillRect(this.x, this.y, this.width, 15);
        context.fillStyle = 'saddlebrown';
        context.fillRect(this.x, this.y + 15, this.width, 35);
        context.stroke();
    }

    move(context) {
        context.clearRect(this.x, this.y, this.width, this.height);
        this.x -= speed;
        this.draw(context)
    }

    collision() {
        if (Player.y + Player.height > this.y && Player.y + Player.height < this.y + this.height && Player.x < this.x + this.width && Player.x + Player.width > this.x) {
            Player.y -= gravity;
            platforms++;
        }
    }
}

class coin {
    constructor(x, y, radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    draw(context) {
        context.beginPath();
        context.arc(this.x, this.y, this.radius, 0, 2 * Math.PI);
        context.fillStyle = 'yellow';
        context.fill();
        context.stroke();
    }

    move(context) {
        context.clearRect(this.x - this.radius - 1, this.y - this.radius - 1, this.radius * 2 + 2, this.radius * 2 + 2);
        this.x -= speed;
        this.draw(context);
    }

    collision(context) {
        if (Player.x + Player.width > this.x - this.radius && Player.x < this.x + this.radius && Player.y < this.y + this.radius && Player.y + Player.height > this.y - this.radius) {
            score++;
            context.clearRect(this.x - this.radius - 1, this.y - this.radius - 1, this.radius * 2 + 2, this.radius * 2 + 2);
            this.x = -100;
            this.y = -100;
            coin_audio.play();
            setTimeout(stop_audio, 400);
        }
    }
}

class obstacle {
    constructor(x, y, width, height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    draw(context) {
        context.beginPath();
        color_change++;
        if (color_change == 20) {
            color_change = 0
            red = !red;
        }
        if (red) {
            context.fillStyle = 'crimson';
        } else {
            context.fillStyle = 'khaki';
        }
        context.fillRect(this.x, this.y, this.width, this.height);
        context.stroke();
    }

    move(context) {
        context.clearRect(this.x, this.y, this.width, this.height);
        this.x -= speed;
        this.draw(context)
    }

    collision() {
        if (Player.x + Player.width > this.x && Player.x < this.x + this.width && Player.y < this.y + this.height && Player.y + Player.height > this.y) {
            end = true;
        }
    }
}

