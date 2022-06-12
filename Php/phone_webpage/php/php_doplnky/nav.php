<?php
	$lang = $_GET['lang'];
	if ($lang == "eng"){
		echo "<ul class=\"nav\">";
			if ($stranka == 1){echo"<li class=\"selected\"><a href=\"zakl_informacie.php?lang=eng\">General information</a></li>";}
			else {echo"<li><a href=\"zakl_informacie.php?lang=eng\">General information</a></li>";}
			if ($stranka == 2){echo"<li class=\"selected\"><a href=\"parametre.php?lang=eng\">Parameters</a></li>";}
			else {echo"<li><a href=\"parametre.php?lang=eng\">Parameters</a></li>";}
			if ($stranka == 3){echo"<li class=\"selected\"><a href=\"porovnanie.php?lang=eng\">Comparison</a></li>";}
			else {echo"<li><a href=\"porovnanie.php?lang=eng\">Comparison</a></li>";}
			if ($stranka == 4){echo"<li class=\"selected\"><a href=\"FAQ.php?lang=eng\">FAQ</a></li>";}
			else {echo"<li><a href=\"FAQ.php?lang=eng\">FAQ</a></li>";}
			if ($stranka == 5){echo"<li class=\"selected\"><a href=\"galeria.php?lang=eng\">Gallery</a></li>";}
			else {echo"<li><a href=\"galeria.php?lang=eng\">Gallery</a></li>";}
			if ($stranka == 6){echo"<li class=\"selected\"><a href=\"recenzie.php?lang=eng\">Reviews</a></li>";}
			else {echo"<li><a href=\"recenzie.php?lang=eng\">Reviews</a></li>";}
			if ($stranka == 7){echo"<li class=\"selected\"><a href=\"kontakt.php?lang=eng\">Contact</a></li>";}
			else {echo"<li><a href=\"kontakt.php?lang=eng\">Contact</a></li>";}
		echo"</ul>";
	}
	else{
		echo "<ul class=\"nav\">";
			if ($stranka == 1){echo"<li class=\"selected\"><a href=\"zakl_informacie.php\">Základné informácie</a></li>";}
			else {echo"<li><a href=\"zakl_informacie.php\">Základné informácie</a></li>";}
			if ($stranka == 2){echo"<li class=\"selected\"><a href=\"parametre.php\">Parametre</a></li>";}
			else {echo"<li><a href=\"parametre.php\">Parametre</a></li>";}
			if ($stranka == 3){echo"<li class=\"selected\"><a href=\"porovnanie.php\">Porovnanie</a></li>";}
			else {echo"<li><a href=\"porovnanie.php\">Porovnanie</a></li>";}
			if ($stranka == 4){echo"<li class=\"selected\"><a href=\"FAQ.php\">FAQ</a></li>";}
			else {echo"<li><a href=\"FAQ.php\">FAQ</a></li>";}
			if ($stranka == 5){echo"<li class=\"selected\"><a href=\"galeria.php\">Galéria</a></li>";}
			else {echo"<li><a href=\"galeria.php\">Galéria</a></li>";}
			if ($stranka == 6){echo"<li class=\"selected\"><a href=\"recenzie.php\">Recenzie</a></li>";}
			else {echo"<li><a href=\"recenzie.php\">Recenzie</a></li>";}
			if ($stranka == 7){echo"<li class=\"selected\"><a href=\"kontakt.php\">Kontakt</a></li>";}
			else {echo"<li><a href=\"kontakt.php\">Kontakt</a></li>";}		
		echo"</ul>";
	}
?>
