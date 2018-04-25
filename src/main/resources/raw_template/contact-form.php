<?php
$to="demo@email.com";
/*Your Email*/
$subject="Message from the website - Contact Form";
/*Issue*/
$date=date("l, F jS, Y");
$time=date("h:i A");

$name=$_REQUEST['name'];
$email=$_REQUEST['email'];
$subject=$_REQUEST['subject'];
$message=$_REQUEST['message'];


$msg="
	
	Suscription request from website on date  $date, hour: $time.\n
	
	name: $name\n
	email: $email\n
	subject: $subject\n	
	message: $message\n		
	";
if($email=="") {
echo "<div class='alert alert-danger'>
		  <a class='close' data-dismiss='alert'>×</a>
		  <strong>Warning!</strong> Please fill all the fields.
	  </div>";
} else {
mail($to,$subject,$msg,"From:".$email);
echo "<div class='alert alert-success'>
		  <a class='close' data-dismiss='alert'>×</a>
		  <strong>Thank you for your message!</strong>
	  </div>";
}
?>
