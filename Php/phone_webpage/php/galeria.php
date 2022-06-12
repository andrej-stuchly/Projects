<!DOCTYPE html>
<html lang="sk">
<head>
	<meta charset="UTF-8">
	<title>LG Q60 - Galéria/Gallery</title>
	<link rel="stylesheet" type="text/css" href="../css/css.css?css=<?php echo time(); ?>">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
</head>
<body>
	<header>
		<?php include 'php_doplnky/header.php';?>
	</header>
	<nav>
		<?php $stranka = 5; include 'php_doplnky/nav.php';?>
	</nav>
	<?php
		$lang = $_GET['lang'];
		if ($lang == "eng"){
			include 'php_doplnky/eng.php';
		}
		else{
			include 'php_doplnky/sk.php';
		}
		echo "<p>".$gal['text1']."</p><br>";
		echo "<div>";
			echo "<p>".$gal['text2']."</p><br>";
			$type = "gif";
			for ($i = 1; $i < 4; $i++){
				if ($i != 1){
					$type = "jpg";
				}
				echo "<img src=\"../visual/$i.$type\" class=\"header gal_$i\">";
			}
			echo "<video width=\"250\" height=\"250\" controls muted>";
				echo "<source src=\"../visual/4.mp4\" type=\"video/mp4\">";
			echo "</video>";
		echo "</div><br>";	
		echo "<div>";
			echo "<p>".$gal['text3']."</p><br>";
			$type = "png";
			for ($i = 5; $i < 9; $i++){
				if ($i == 8){
					$type = "gif";
				}
				echo "<img src=\"../visual/$i.$type\" class=\"header gal_rest\">";
			}
			echo "<video width=\"320\" height=\"320\" controls>";
			echo "<source src=\"../visual/9.mp4\" type=\"video/mp4\">";
			echo "</video>";
		echo "</div><br>";	
		echo "<div>";
			echo "<p>".$gal['text4']."</p>";
			echo "<ul>";
				for ($i = 0; $i < 12; $i++ ){
					echo "<li>".$gal['text5'][$i]."</li>";
				}
			echo "</ul>";
			echo "<p>".$gal['text6']."</p>";
			for ($i = 1; $i < 4; $i++){
				echo "<audio controls><source src=\"../audio/$i.ogg\" type=\"audio/ogg\"></audio>";
			}
		?>
	</div>
</body>
</html>
