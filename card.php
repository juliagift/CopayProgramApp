<!DOCTYPE html>
<html>
    <head>
        <title>Copay Card</title>
        <link rel="stylesheet" type="text/css" href="./cardstyles.css" />
    </head>
    <body>
        <div class="card_container">
            <div class="card" data-label="<?php echo $_GET["drugName"]; ?>">
                <div class="card-container">
                    <h1 style="color: purple"><?php echo $_GET["firstName"]; ?> <?php echo $_GET["lastName"]; ?></h1>
                    <br /><br /><br /><br /><br /><br /><br />
                    <h3><?php echo $_GET["state"]; ?>, <?php echo $_GET["zip"]; ?></h3>
                    <br />
                    <h3 >DOB: <?php echo $_GET["dob"]; ?></h3>
                    <br /><br /><br />
                    <h3 id = "random"></h3>
                </div>
                <br/>
                <br/>
            
                <div>
                    <h3 id="copay"><b>Learn more about copay programs</b></h3>
                <div>
            </div>

            <video width="530" height="320" controls>
                <source src="./copay.mp4" type="video/mp4">
            </video>

            <a id="home" href="./home.html">Back to Home Page</a>
            <br/>
            <a id="registration" href="./registration.html">Back to Registration Page</a>
        </div>
        <script type="text/javascript" src="./index.js"></script>
    </body>
</html>
