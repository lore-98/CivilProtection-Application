-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: db_sistema_centrale
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `accadimento`
--

DROP TABLE IF EXISTS `accadimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accadimento` (
  `idaccadimento` int NOT NULL AUTO_INCREMENT,
  `cap` int NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `sorgente` varchar(200) NOT NULL,
  `tempo` timestamp NULL DEFAULT NULL,
  `precipitazione` double DEFAULT NULL,
  `magnitudo` double DEFAULT NULL,
  PRIMARY KEY (`idaccadimento`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accadimento`
--

LOCK TABLES `accadimento` WRITE;
/*!40000 ALTER TABLE `accadimento` DISABLE KEYS */;
INSERT INTO `accadimento` VALUES (96,90121,'Vento','3bMeteo','2020-05-24 19:41:02',10.6,NULL),(97,90121,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-24 19:41:10',NULL,3.6),(100,87010,'Pioggia','3bMeteo','2020-05-24 19:41:53',26.7,NULL),(101,22020,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-24 19:42:10',NULL,4.9),(102,23895,'Grandine','3bMeteo','2020-05-24 19:42:18',11.5,NULL),(103,10121,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-24 19:42:40',NULL,2.7),(104,22020,'Grandine','3bMeteo','2020-05-24 19:42:43',16.7,NULL),(105,22030,'Vento','3bMeteo','2020-05-25 14:49:37',25.3,NULL),(107,22030,'Neve','3bMeteo','2020-05-25 14:50:02',1.9,NULL),(108,20100,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:50:15',NULL,3.4),(109,80010,'Pioggia','3bMeteo','2020-05-25 14:50:27',14.7,NULL),(111,10121,'Vento','3bMeteo','2020-05-25 14:50:52',5.4,NULL),(112,80010,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:51:16',NULL,3.1),(114,90121,'Grandine','3bMeteo','2020-05-25 14:51:42',10,NULL),(115,22020,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:51:46',NULL,4.2),(116,23895,'Grandine','3bMeteo','2020-05-25 14:52:07',18,NULL),(117,59013,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:52:16',NULL,4),(118,23895,'Neve','3bMeteo','2020-05-25 14:52:33',0.3,NULL),(119,23895,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:52:46',NULL,5.3),(120,22030,'Pioggia','3bMeteo','2020-05-25 14:52:58',6.5,NULL),(121,71010,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:53:16',NULL,4.7),(122,59013,'Pioggia','3bMeteo','2020-05-25 14:53:23',33.9,NULL),(124,90121,'Pioggia','3bMeteo','2020-05-25 14:53:48',9.6,NULL),(125,30100,'Grandine','3bMeteo','2020-05-25 14:54:13',11.1,NULL),(126,30100,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:54:16',NULL,5),(127,50100,'Pioggia','3bMeteo','2020-05-25 14:54:38',14.9,NULL),(128,50100,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:54:46',NULL,5),(129,22020,'Neve','3bMeteo','2020-05-25 14:55:03',0,NULL),(130,90121,'Terremoto','Istituto Nazionale di Geofisica e Vulcanologia','2020-05-25 14:55:16',NULL,6.4),(131,23895,'Pioggia','3bMeteo','2020-05-25 14:55:28',3.9,NULL);
/*!40000 ALTER TABLE `accadimento` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-25 17:08:45
