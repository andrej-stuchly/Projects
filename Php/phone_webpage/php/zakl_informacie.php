<!DOCTYPE html>
<html lang="sk">
<head>
	<meta charset="UTF-8">
	<title>LG Q60 - Základné informácie/General information</title>
	<link rel="stylesheet" type="text/css" href="../css/css.css?css=<?php echo time(); ?>">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
</head>
<body>
	<header>
		<?php include 'php_doplnky/header.php';?>
	</header>
	<nav>
		<?php $stranka = 1 ;include 'php_doplnky/nav.php';?>
	</nav>
	<div>
		<img src="../visual/zad.png" alt="fotka telefónu zozadu" class="intro_image1">
		<img src="../visual/pred.png" alt="fotka telefónu spredu" class="intro_image2">
		<?php
			$lang = $_GET['lang'];
			if ($lang == "eng"){
				include 'php_doplnky/eng.php';
			}
			else{
				include 'php_doplnky/sk.php';
			}
			echo "<p class=\"intro_text\">".$zak['text1']."</p>";
			echo "<p class=\"intro_text\">".$zak['text2']."</p>";
		?>
	</div>
</body>
</html>
