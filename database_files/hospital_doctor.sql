-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: hospital
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `available_from` time DEFAULT NULL,
  `available_to` time DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  `profile_img` varchar(255) DEFAULT 'images/doctors/default.png',
  PRIMARY KEY (`id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Ruvindu G.','ruvindu@example.lk','08:00:00','16:00:00','0711234567','123 Galle Road, Colombo','password123',1,'images/doctors/ruvindu.jpg'),(2,'Sithum G.','sithum@example.lk','09:00:00','17:00:00','0717654321','456 Kandy Rd, Kandy','password456',1,'images/doctors/sithum.jpg'),(3,'Kisanaja G.','kisanja@example.lk','07:00:00','15:00:00','0711112233','789 Horton Place, Colombo 7','password789',1,'images/doctors/kisanja.jpg'),(4,'Ashan G.','ashan@example.lk','10:00:00','18:00:00','0772233445','102 Matara Rd, Galle','password321',1,'images/doctors/ashan.jpg'),(5,'Nishal Kumara','nishal.kumara@example.lk','08:30:00','16:30:00','0774455667','67 Kurunegala Rd, Kurunegala','password654',2,'images/doctors/default.png'),(6,'Shamali Karuna','shamali.karuna@example.lk','11:00:00','19:00:00','0775566778','89 Badulla Rd, Nuwara Eliya','password987',3,'images/doctors/default.png'),(7,'Sandun Fernando','sandun.fernando@example.lk','06:00:00','14:00:00','0716677889','152 Batticaloa Rd, Batticaloa','password159',1,'images/doctors/default.png'),(8,'Kumudu Dias','kumudu.dias@example.lk','09:30:00','17:30:00','0717788990','24 Negombo Rd, Negombo','password753',2,'images/doctors/default.png'),(9,'Ranil Perera','ranil.perera@example.lk','07:30:00','15:30:00','0778899001','75 Gampaha Rd, Gampaha','password951',3,'images/doctors/default.png'),(10,'Anula Bandara','anula.bandara@example.lk','12:00:00','20:00:00','0719900112','15 Kegalle Rd, Kegalle','password258',2,'images/doctors/default.png'),(11,'Anura Kumara','anura.kumara@example.lk','08:00:00','16:00:00','0771296567','179 Galle Road, Colombo','password1239',4,'images/doctors/anura_kumara.jpg'),(12,'Sajith Premadasa','sajith.premadasa@example.lk','12:00:00','20:00:00','0719910012','99 Kegalle Rd, Kegalle','password25998',5,'images/doctors/sajith_premadasa.png'),(13,'John Doe','johndoe@example.com','09:00:00','17:00:00','1234567890','123 Main St','password123',1,'images/doctors/default.png'),(14,'Jane Smith','janesmith@example.com','10:00:00','18:00:00','0987654321','456 Elm St','password456',2,'images/doctors/default.png'),(15,'Alice Johnson','alicejohnson@example.com','08:00:00','16:00:00','1112223333','789 Oak St','password789',3,'images/doctors/default.png'),(16,'Bob Williams','bobwilliams@example.com','11:00:00','19:00:00','4445556666','321 Pine St','password101',4,'images/doctors/default.png'),(17,'Carol Davis','caroldavis@example.com','12:00:00','20:00:00','7778889999','654 Cedar St','password202',5,'images/doctors/default.png'),(18,'Dan Brown','danbrown@example.com','07:00:00','15:00:00','1010101010','987 Maple St','password303',6,'images/doctors/default.png'),(19,'Emma Garcia','emmagarcia@example.com','08:30:00','16:30:00','2020202020','741 Birch St','password404',7,'images/doctors/default.png'),(20,'Frank Martinez','frankmartinez@example.com','09:30:00','17:30:00','3030303030','852 Walnut St','password505',8,'images/doctors/default.png'),(21,'Grace Lee','gracelee@example.com','10:30:00','18:30:00','4040404040','963 Fir St','password606',9,'images/doctors/default.png'),(22,'Henry Clark','henryclark@example.com','07:30:00','15:30:00','5050505050','159 Ash St','password707',1,'images/doctors/default.png'),(23,'Ivy Walker','ivywalker@example.com','11:00:00','19:00:00','6060606060','753 Spruce St','password808',2,'images/doctors/default.png'),(24,'Jack Young','jackyoung@example.com','12:30:00','20:30:00','7070707070','951 Poplar St','password909',3,'images/doctors/default.png'),(25,'Kate Hernandez','katehernandez@example.com','08:00:00','16:00:00','8080808080','753 Willow St','password111',4,'images/doctors/default.png'),(26,'Liam Perez','liamperez@example.com','09:00:00','17:00:00','9090909090','357 Cypress St','password222',5,'images/doctors/default.png'),(27,'Mia Hall','miahall@example.com','10:00:00','18:00:00','1112131415','852 Cherry St','password333',6,'images/doctors/default.png'),(28,'Noah King','noahking@example.com','11:00:00','19:00:00','1516171819','654 Redwood St','password444',7,'images/doctors/default.png'),(29,'Olivia Scott','oliviascott@example.com','12:00:00','20:00:00','1920212223','321 Sequoia St','password555',8,'images/doctors/default.png'),(30,'Paul Adams','pauladams@example.com','07:00:00','15:00:00','2324252627','987 Palm St','password666',9,'images/doctors/default.png'),(31,'Quinn Nelson','quinnnelson@example.com','08:30:00','16:30:00','2728293031','159 Sycamore St','password777',3,'images/doctors/default.png'),(32,'Ruby Ramirez','rubyramirez@example.com','09:30:00','17:30:00','3132333435','753 Aspen St','password888',2,'images/doctors/default.png');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-14  5:53:13
