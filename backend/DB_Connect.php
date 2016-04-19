<?php
    class DB_Connect {     
        // Constructor
        function __construct() {
             
        }
     
        // Destructor
        function __destruct() {
            $this->close();
        }
     
        // Connect to database
        public function connect() {
        	// Require configuration file
            require_once("config.php");

            // Connect to mysql
            $connection = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);

            // Selecting database
            mysql_select_db(DB_DATABASE);
     
            // Return database handler
            return $connection;
        }
     
        // Closing database connection
        public function close() {
            mysql_close();
        }
    }
?>