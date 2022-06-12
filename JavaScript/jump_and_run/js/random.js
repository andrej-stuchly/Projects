let Platform = new platform(350,460,805,50);

function game_start(){
	var canvas = document.getElementById("canvas");
	var context = canvas.getContext("2d");
	
	Player.draw(context);
	Player.jump_up(context);
	
	Platform.draw(context);

	Platform.y -= 380;
	Platform.draw(context);

	let Coin = new coin(650,420,30);
	Coin.draw(context);
	
	let Obstacle = new obstacle(900,390,45,70);
	Obstacle.draw(context);	
	Obstacle.move(context);
	game();
}




