<?php
    class DB_Functions {
        private $db;
        
        // Constructor
        function __construct() {
            // Require connection to database
            require_once("DB_Connect.php");
            // Connect to database
            $this->db = new DB_Connect();
            $this->db->connect();
        }
     
        // Destructor
        function __destruct() {
             
        }

        // Check if user is existing or not
        public function isUserExisting($email) {
            $query = "SELECT * FROM Users WHERE email = '$email'";
            $result = mysql_query($query);
            $rows = mysql_num_rows($result);

            if ($rows > 0) {
                // User existed 
                return true;
            } else {
                // User not existed
                return false;
            }
        }

        // Register user
        public function registerUser($email, $nickname, $password) {
            $query = "INSERT INTO Users(email, nickname, password) VALUES ('$email', '$nickname', '$password')";
            $result = mysql_query($query);
            
            return true;
        }
    }
?>