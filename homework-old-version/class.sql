-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2020-03-14 12:49:41
-- 服务器版本： 8.0.17
-- PHP 版本： 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `class`
--

-- --------------------------------------------------------

--
-- 表的结构 `homework`
--

CREATE TABLE `homework` (
  `hid` int(11) NOT NULL,
  `title` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tid` int(11) NOT NULL,
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `homework`
--

INSERT INTO `homework` (`hid`, `title`, `content`, `tid`, `create_time`, `update_time`) VALUES
(1, '1', '1', 1, '2020-03-07 23:06:00', '2020-03-08 00:00:00'),
(8, '666', '777', 2, '2020-03-13 14:45:34', '2020-03-13 14:45:34');

-- --------------------------------------------------------

--
-- 表的结构 `student`
--

CREATE TABLE `student` (
  `sid` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(12) NOT NULL,
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `student`
--

INSERT INTO `student` (`sid`, `name`, `password`, `create_time`, `update_time`) VALUES
(1, '景点1', '123', '2020-03-07 23:06:00', '2020-03-13 14:38:15'),
(3, 'zephyr', '666', '2020-03-13 13:57:19', '2020-03-13 13:57:19');

-- --------------------------------------------------------

--
-- 表的结构 `student_homework`
--

CREATE TABLE `student_homework` (
  `sid` int(11) NOT NULL,
  `hid` int(11) NOT NULL,
  `submit_content` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `submit_time` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `student_homework`
--

INSERT INTO `student_homework` (`sid`, `hid`, `submit_content`, `submit_time`) VALUES
(1, 1, NULL, NULL),
(1, 8, NULL, NULL),
(3, 8, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `teach`
--

CREATE TABLE `teach` (
  `sid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `teach`
--

INSERT INTO `teach` (`sid`, `tid`, `create_time`) VALUES
(1, 2, '2020-03-07 23:06:00'),
(3, 2, '2020-03-13 13:57:19');

-- --------------------------------------------------------

--
-- 表的结构 `teacher`
--

CREATE TABLE `teacher` (
  `tid` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(12) NOT NULL,
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `teacher`
--

INSERT INTO `teacher` (`tid`, `name`, `password`, `create_time`, `update_time`) VALUES
(1, 'kkllee', '123456', '2020-03-09 22:00:00', '2020-03-09 19:00:00'),
(2, 'kle', '123', '2020-03-13 13:39:20', '2020-03-13 13:39:20'),
(5, 'uncle cat', 'forever', '2020-03-13 13:47:19', '2020-03-13 13:47:19');

--
-- 转储表的索引
--

--
-- 表的索引 `homework`
--
ALTER TABLE `homework`
  ADD PRIMARY KEY (`hid`);

--
-- 表的索引 `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`sid`);

--
-- 表的索引 `student_homework`
--
ALTER TABLE `student_homework`
  ADD PRIMARY KEY (`sid`,`hid`),
  ADD KEY `hid` (`hid`);

--
-- 表的索引 `teach`
--
ALTER TABLE `teach`
  ADD PRIMARY KEY (`sid`,`tid`),
  ADD KEY `tid` (`tid`);

--
-- 表的索引 `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`tid`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `homework`
--
ALTER TABLE `homework`
  MODIFY `hid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- 使用表AUTO_INCREMENT `student`
--
ALTER TABLE `student`
  MODIFY `sid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- 使用表AUTO_INCREMENT `teacher`
--
ALTER TABLE `teacher`
  MODIFY `tid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- 限制导出的表
--

--
-- 限制表 `student_homework`
--
ALTER TABLE `student_homework`
  ADD CONSTRAINT `student_homework_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `student_homework_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `student_homework_ibfk_3` FOREIGN KEY (`hid`) REFERENCES `homework` (`hid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `teach`
--
ALTER TABLE `teach`
  ADD CONSTRAINT `teach_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `teach_ibfk_3` FOREIGN KEY (`tid`) REFERENCES `teacher` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
