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
            // Prevent injection
            $email = mysql_real_escape_string($email);

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

        // Get user by email
        public function getUserByUserID($email) {
            // Prevent injection
            $email = mysql_real_escape_string($email);

            $query = "SELECT * FROM Users WHERE email = '$email'";
            $result = mysql_query($query) or die(mysql_error());
            // Check for result 
            $rows = mysql_num_rows($result);
            if ($rows > 0) {
                $result = mysql_fetch_array($result);
                return $result;
            } else {
                // user not found
                return false;
            }
        }

        // Register user
        public function registerUser($email, $name, $password) {
            // Prevent injection
            $email = mysql_real_escape_string($email);
            $name = mysql_real_escape_string($name);
            $password = mysql_real_escape_string($password);

            $query = "INSERT INTO Users(email, name, password) VALUES ('$email', '$name', '$password')";
            $result = mysql_query($query);
            
            return true;
        }

        // Get all users in the system
        public function getAllUsers() {
            $query = "SELECT * FROM Users";
            $result = mysql_query($query) or die(mysql_error());
            $array = array();
            
            // Check for result 
            while ($temp = mysql_fetch_array($result, MYSQL_ASSOC)) {
                // Add every user to array
                array_push($array, $temp);
            }
            return ($array);
        }

        public function createTask($task_name, $task_duration, $task_owner, $task_PairProgrammer1ID, $task_PairProgrammer2ID) {
            // Prevent injection
            $task_name = mysql_real_escape_string($task_name);
            $task_duration = mysql_real_escape_string($task_duration);
            $task_owner = mysql_real_escape_string($task_owner);
            $task_PairProgrammer1ID = mysql_real_escape_string($task_PairProgrammer1ID);
            $task_PairProgrammer2ID = mysql_real_escape_string($task_PairProgrammer2ID);

            $query1 = "INSERT INTO Tasks(name, total_time, owner) VALUES ('$task_name', '$task_duration', '$task_owner')";
            $result1 = mysql_query($query1) or die(mysql_error());

            $query2 = "SELECT task_id FROM Tasks WHERE name = '$task_name' AND owner = '$task_owner' ORDER BY task_id DESC LIMIT 1;";
            $result2 = mysql_query($query2) or die(mysql_error());
            $row = mysql_fetch_assoc($result2);

            $task_id = $row["task_id"];

            $query3 = "INSERT INTO TaskAssign(email, task_id) VALUES
                ('$task_PairProgrammer1ID', '$task_id'),
                ('$task_PairProgrammer2ID', '$task_id')";
            $result3 = mysql_query($query3) or die(mysql_error());

            return $result1 AND $result2 AND $result3;
        }

        public function deleteTask($task_id) {
            // Prevent injection
            $task_id = mysql_real_escape_string($task_id);

            $query = "DELETE FROM Tasks WHERE task_id = '$task_id'";
            $result = mysql_query($query) or die(mysql_error());
            return $result;
        }
    }
?>