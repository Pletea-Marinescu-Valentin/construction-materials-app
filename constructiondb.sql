-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 17, 2025 at 01:48 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `constructiondb`
--

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`id`, `user_id`) VALUES
(7, 7);

-- --------------------------------------------------------

--
-- Table structure for table `cart_items`
--

CREATE TABLE `cart_items` (
  `id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `cart_id` bigint(20) NOT NULL,
  `material_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `constructionmaterial`
--

CREATE TABLE `constructionmaterial` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `stock` int(11) NOT NULL,
  `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `constructionmaterial`
--

INSERT INTO `constructionmaterial` (`id`, `name`, `type`, `price`, `stock`, `image`) VALUES
(1, 'Thermal Insulation Board A', 'thermal_insulation', 12.99, 150, NULL),
(2, 'Thermal Insulation Roll B', 'thermal_insulation', 25.5, 80, NULL),
(3, 'Thermal Insulation Foam C', 'thermal_insulation', 9.75, 120, NULL),
(4, 'Thermal Insulation Spray D', 'thermal_insulation', 18.4, 50, NULL),
(5, 'Thermal Insulation Sheet E', 'thermal_insulation', 11.3, 200, NULL),
(6, 'Waterproof Membrane A', 'waterproofing', 20.99, 100, NULL),
(7, 'Waterproof Paint B', 'waterproofing', 15.5, 75, NULL),
(8, 'Waterproof Coating C', 'waterproofing', 30.75, 50, NULL),
(9, 'Waterproof Sealant D', 'waterproofing', 10.4, 120, NULL),
(10, 'Waterproof Sheet E', 'waterproofing', 14.2, 90, NULL),
(11, 'Cement Bag A', 'cement', 8.99, 500, NULL),
(12, 'Mortar Mix B', 'cement', 6.5, 400, NULL),
(13, 'Plaster Powder C', 'cement', 7.75, 300, NULL),
(14, 'Screed Mix D', 'cement', 12.4, 250, NULL),
(15, 'Ready-Mix Cement E', 'cement', 10.3, 600, NULL),
(16, 'Roof Tile A', 'roofs', 2.99, 1000, NULL),
(17, 'Roof Panel B', 'roofs', 25.5, 300, NULL),
(18, 'Roof Shingle C', 'roofs', 15.75, 700, NULL),
(19, 'Roof Insulation D', 'roofs', 22.4, 150, NULL),
(20, 'Roof Coating E', 'roofs', 18.9, 100, NULL),
(21, 'Wood Beam A', 'wood', 50, 100, NULL),
(22, 'Wood Plank B', 'wood', 25, 200, NULL),
(23, 'Wood Sheet C', 'wood', 15, 300, NULL),
(24, 'Plywood Panel D', 'wood', 35, 150, NULL),
(25, 'Hardwood Board E', 'wood', 75, 50, NULL),
(26, 'Drywall Panel A', 'walls', 10.99, 500, NULL),
(27, 'Ceiling Tile B', 'walls', 12.5, 400, NULL),
(28, 'Partition Wall C', 'walls', 20.75, 300, NULL),
(29, 'Gypsum Board D', 'walls', 15.4, 200, NULL),
(30, 'Ceiling Insulation E', 'walls', 22.3, 150, NULL),
(31, 'Brick A', 'masonry', 0.99, 2000, NULL),
(32, 'Concrete Block B', 'masonry', 3.5, 1500, NULL),
(33, 'Reinforcement Steel C', 'masonry', 10, 800, NULL),
(34, 'Steel Beam D', 'masonry', 100, 100, NULL),
(35, 'Masonry Mortar E', 'masonry', 7.5, 700, NULL),
(36, 'Concrete Mixer A', 'mixers', 250, 10, NULL),
(37, 'Wheelbarrow B', 'mixers', 80, 30, NULL),
(38, 'Raffia Bag C', 'mixers', 1, 1000, NULL),
(39, 'Heavy Duty Mixer D', 'mixers', 300, 5, NULL),
(40, 'Lightweight Mixer E', 'mixers', 200, 15, NULL),
(41, 'Scaffold Set A', 'scaffolding', 500, 20, NULL),
(42, 'Aluminum Ladder B', 'scaffolding', 100, 50, NULL),
(43, 'Folding Ladder C', 'scaffolding', 75, 60, NULL),
(44, 'Scaffold Tower D', 'scaffolding', 800, 10, NULL),
(45, 'Extension Ladder E', 'scaffolding', 120, 40, NULL),
(46, 'PVC Pipe A', 'drainage', 5, 1000, NULL),
(47, 'Manhole Cover B', 'drainage', 50, 100, NULL),
(48, 'Drainage System C', 'drainage', 300, 20, NULL),
(49, 'Drain Grate D', 'drainage', 20, 150, NULL),
(50, 'Sewer Pipe E', 'drainage', 30, 200, NULL),
(51, 'Rain Gutter A', 'rainwater_systems', 15, 200, NULL),
(52, 'Rainwater Tank B', 'rainwater_systems', 250, 30, NULL),
(53, 'Downspout C', 'rainwater_systems', 20, 150, NULL),
(54, 'Rainwater Filter D', 'rainwater_systems', 50, 100, NULL),
(55, 'Rainwater Diverter E', 'rainwater_systems', 35, 80, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `orderdetails`
--

CREATE TABLE `orderdetails` (
  `id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `material_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price_per_unit` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `order_date` datetime DEFAULT current_timestamp(),
  `total_price` decimal(38,2) NOT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `customer_contact` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `material_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `role` varchar(255) NOT NULL,
  `reset_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `created_at`, `role`, `reset_token`) VALUES
(1, 'admin', '$2a$10$dnwdsiAqGN1DFSm2Cq.gS.rnjRUwVRNNwMqiFy5B2LdokNUuzSwB6', 'admin@example.com', '2024-12-30 13:02:17', 'ADMIN', NULL),
(7, 'valentin', '$2a$10$Iu4XqBpcmUvL3ysTUPe0ZeE9iLl8KtX2S7kX48BJtPWhewm00uUxm', 'pletea.valentin2003@gmail.com', '2025-01-04 12:21:35', 'USER', NULL),
(8, 'user', '$2a$10$IzbEw0j4Arpu/.Kfu6G8t.i/vAzOc9sPulvi26N3Vi2x/5.5qrFWu', 'user@example.com', '2025-01-04 15:50:23', 'USER', NULL),
(15, 'aaa', '$2a$10$MonLuS3AlUTA0FgOwtWmKedixvXly2nTcVppqloGh6/NHcjVo9XXa', 'aaa@doamin.ro', '2025-01-11 00:18:16', 'USER', NULL),
(16, 'user2', '$2a$10$oT/PFyMM5Bofg27R61zrVOFEtXd8Vs7fLj9w3PEu88Tx1Vxq7Jicu', 'user2@domain.ro', '2025-01-12 16:55:46', 'USER', NULL),
(17, 'user7', '$2a$10$HRf4wscOWj0Q1wVQunJHE.Dk9o1gVMHPEiOqPv.i9j0t3Nw5UVYZ2', 'user7@domain.ro', '2025-01-15 11:35:59', 'USER', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK64t7ox312pqal3p7fg9o503c2` (`user_id`);

--
-- Indexes for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpcttvuq4mxppo8sxggjtn5i2c` (`cart_id`),
  ADD KEY `FKqx0sbipyw8ad23a9g1dwmqtjm` (`material_id`);

--
-- Indexes for table `constructionmaterial`
--
ALTER TABLE `constructionmaterial`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order` (`order_id`),
  ADD KEY `fk_material` (`material_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK95y6qagnoucbq8751utfy1uc8` (`material_id`),
  ADD KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `carts`
--
ALTER TABLE `carts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `cart_items`
--
ALTER TABLE `cart_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `constructionmaterial`
--
ALTER TABLE `constructionmaterial`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `orderdetails`
--
ALTER TABLE `orderdetails`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `FKpcttvuq4mxppo8sxggjtn5i2c` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`),
  ADD CONSTRAINT `FKqx0sbipyw8ad23a9g1dwmqtjm` FOREIGN KEY (`material_id`) REFERENCES `constructionmaterial` (`id`);

--
-- Constraints for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD CONSTRAINT `fk_material` FOREIGN KEY (`material_id`) REFERENCES `constructionmaterial` (`id`),
  ADD CONSTRAINT `fk_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `FK95y6qagnoucbq8751utfy1uc8` FOREIGN KEY (`material_id`) REFERENCES `constructionmaterial` (`id`),
  ADD CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
