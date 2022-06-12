<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>LG Q60 - Recenzia/Review</title>
	<link rel="stylesheet" type="text/css" href="../css/css.css?css=<?php echo time(); ?>">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
</head>
<body>
	<header>
		<?php include 'php_doplnky/header.php';?>
	</header>
	<nav>
		<?php include 'php_doplnky/nav.php';?>
	</nav>
	<?php	
		$lang = $_GET['lang'];
		if ($lang == "eng"){
			include 'php_doplnky/eng.php';
		}
		else{
			include 'php_doplnky/sk.php';
		}
		$file = fopen("php_doplnky/recenzia.txt", "a");
		$review = $_POST["review"];
		echo "<p>".$form['text1']."</p>";
		if (!empty($review)){
			fwrite($file, $_POST["review"].PHP_EOL);
			fclose($file);
			echo $review;
			echo "<p>".$form['text2']."</p>";
		}
		else{
			echo "<p><b>".$form['text3']."</b></p>";
		}
	?>
	<br> <br>
</body>
</html>
