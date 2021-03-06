USE [master]
GO
/****** Object:  Database [QuizOnlineDB]    Script Date: 2/3/2021 11:16:33 PM ******/
CREATE DATABASE [QuizOnlineDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QuizOnlineDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\QuizOnlineDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QuizOnlineDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\QuizOnlineDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QuizOnlineDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QuizOnlineDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QuizOnlineDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QuizOnlineDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [QuizOnlineDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QuizOnlineDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [QuizOnlineDB] SET  MULTI_USER 
GO
ALTER DATABASE [QuizOnlineDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QuizOnlineDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QuizOnlineDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QuizOnlineDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
USE [QuizOnlineDB]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 2/3/2021 11:16:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[Email] [varchar](30) NOT NULL,
	[Name] [nvarchar](max) NOT NULL,
	[Password] [varchar](max) NOT NULL,
	[Admin] [bit] NOT NULL,
	[Status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Answer]    Script Date: 2/3/2021 11:16:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Answer](
	[AnsID] [varchar](30) NOT NULL,
	[QuestID] [varchar](30) NOT NULL,
	[Content] [nvarchar](max) NOT NULL,
	[CorrectAnswer] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[AnsID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Question]    Script Date: 2/3/2021 11:16:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Question](
	[QuestID] [varchar](30) NOT NULL,
	[SubID] [varchar](30) NOT NULL,
	[Content] [nvarchar](max) NOT NULL,
	[CreateDate] [datetime] NOT NULL,
	[Status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[QuestID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ResultSubject]    Script Date: 2/3/2021 11:16:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ResultSubject](
	[SubID] [varchar](30) NOT NULL,
	[Email] [varchar](30) NOT NULL,
	[Mark] [float] NOT NULL,
	[DateTakeQuiz] [datetime] NOT NULL,
	[DurationTakeQuiz] [bigint] NOT NULL,
	[Data] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK__ResultSu__0706A839002AD846] PRIMARY KEY CLUSTERED 
(
	[SubID] ASC,
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Subject]    Script Date: 2/3/2021 11:16:34 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Subject](
	[SubID] [varchar](30) NOT NULL,
	[SubCodeName] [varchar](30) NOT NULL,
	[SubName] [nvarchar](max) NOT NULL,
	[CreateDate] [datetime] NOT NULL,
	[Duration] [bigint] NOT NULL,
	[Status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[SubID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
INSERT [dbo].[Account] ([Email], [Name], [Password], [Admin], [Status]) VALUES (N'datdtse140920@fpt.edu.vn', N'Đỗ Trọng Đạt', N'22C377DAF265F42E11A57958295C4DA076A6850BDEA51C22FB8B609EB2B55897', 1, 1)
INSERT [dbo].[Account] ([Email], [Name], [Password], [Admin], [Status]) VALUES (N'trongdat2000@gmail.com', N'Trọng Đạt', N'15E2B0D3C33891EBB0F1EF609EC419420C20E320CE94C65FBC8C3312448EB225', 0, 1)
INSERT [dbo].[Account] ([Email], [Name], [Password], [Admin], [Status]) VALUES (N'tronghuy@gmail.com', N'Trọng Huy', N'15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 0, 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_1_1', N'1_1', N'Each node can be reachable from the root
through some paths.', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_1_2', N'1_1', N'Path is number of arcs.', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_1_3', N'1_1', N'The level of a node is the length of the path from the root to the node plus 1.', 1)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_1_4', N'1_1', N'The height of a nonempty tree is the maximum level of a node in the tree.', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_2_1', N'1_2', N'Breath-First traversal is implemented using queue', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_2_2', N'1_2', N'For a binary tree with n nodes, there are n! different traversals', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_2_3', N'1_2', N'The complexity of searching a node is the length of the path leading to this node', 1)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_2_4', N'1_2', N'A search can takes lg ( n ) time units in the worst case', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_3_1', N'1_3', N'Breadth-first search.', 1)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_3_2', N'1_3', N'Depth-first search', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_3_3', N'1_3', N'All of the others', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_3_4', N'1_3', N'None of the others', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_4_1', N'1_4', N'Breath-First Traversal', 1)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_4_2', N'1_4', N'Depth-First Traversal', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_4_3', N'1_4', N'Stackless Depth-First Traversal', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_4_4', N'1_4', N'None of the others', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_5_1', N'1_5', N'10', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_5_2', N'1_5', N'15', 0)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_5_3', N'1_5', N'20', 1)
INSERT [dbo].[Answer] ([AnsID], [QuestID], [Content], [CorrectAnswer]) VALUES (N'1_5_4', N'1_5', N'30', 0)
INSERT [dbo].[Question] ([QuestID], [SubID], [Content], [CreateDate], [Status]) VALUES (N'1_1', N'1', N'Which of the following concepts of tree are true:', CAST(N'2021-02-03T17:15:05.843' AS DateTime), 1)
INSERT [dbo].[Question] ([QuestID], [SubID], [Content], [CreateDate], [Status]) VALUES (N'1_2', N'1', N'Select correct statement', CAST(N'2021-02-03T17:55:33.330' AS DateTime), 1)
INSERT [dbo].[Question] ([QuestID], [SubID], [Content], [CreateDate], [Status]) VALUES (N'1_3', N'1', N'What graph traversal algorithm uses a queue to keep track of vertices which need to', CAST(N'2021-02-03T18:14:12.333' AS DateTime), 1)
INSERT [dbo].[Question] ([QuestID], [SubID], [Content], [CreateDate], [Status]) VALUES (N'1_4', N'1', N'......... will visit nodes of a tree starting from the highest (or lowest) level and moving down (or up) level by level and at a level, it visits nodes from left to right (or from right to left)', CAST(N'2021-02-03T18:24:04.243' AS DateTime), 1)
INSERT [dbo].[Question] ([QuestID], [SubID], [Content], [CreateDate], [Status]) VALUES (N'1_5', N'1', N'A balanced binary search tree containing one million nodes. What is the maximum
number of comparisons needed to find a key in the tree', CAST(N'2021-02-03T18:24:04.243' AS DateTime), 1)
INSERT [dbo].[ResultSubject] ([SubID], [Email], [Mark], [DateTakeQuiz], [DurationTakeQuiz], [Data]) VALUES (N'1', N'trongdat2000@gmail.com', 0, CAST(N'2021-02-03T23:12:01.453' AS DateTime), 0, N'rO0ABXNyACdkYXRkdC5yZXN1bHRzdWJqZWN0LlJlc3VsdFN1YmplY3RPYmplY3TG9OQ3ewWDAgIAAUwACXF1ZXN0aW9uc3QAEExqYXZhL3V0aWwvTGlzdDt4cHNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAFc3IAKGRhdGR0LnJlc3VsdHN1YmplY3QuUmVzdWx0UXVlc3Rpb25PYmplY3QGn4NARLyTxwIAAkwAB2Fuc3dlcnNxAH4AAUwAB2NvbnRlbnR0ABJMamF2YS9sYW5nL1N0cmluZzt4cHNxAH4AAwAAAAR3BAAAAARzcgAmZGF0ZHQucmVzdWx0c3ViamVjdC5SZXN1bHRBbnN3ZXJPYmplY3RFuo9DppnwWAIAA1oAB2Nob29zZW5aAA1jb3JyZWN0QW5zd2VyTAAHY29udGVudHEAfgAGeHAAAHQAEk5vbmUgb2YgdGhlIG90aGVyc3NxAH4ACQAAdAASRGVwdGgtZmlyc3Qgc2VhcmNoc3EAfgAJAAB0ABFBbGwgb2YgdGhlIG90aGVyc3NxAH4ACQABdAAVQnJlYWR0aC1maXJzdCBzZWFyY2gueHQAU1doYXQgZ3JhcGggdHJhdmVyc2FsIGFsZ29yaXRobSB1c2VzIGEgcXVldWUgdG8ga2VlcCB0cmFjayBvZiB2ZXJ0aWNlcyB3aGljaCBuZWVkIHRvc3EAfgAFc3EAfgADAAAABHcEAAAABHNxAH4ACQAAdABJVGhlIGhlaWdodCBvZiBhIG5vbmVtcHR5IHRyZWUgaXMgdGhlIG1heGltdW0gbGV2ZWwgb2YgYSBub2RlIGluIHRoZSB0cmVlLnNxAH4ACQAAdAAXUGF0aCBpcyBudW1iZXIgb2YgYXJjcy5zcQB+AAkAAHQAPUVhY2ggbm9kZSBjYW4gYmUgcmVhY2hhYmxlIGZyb20gdGhlIHJvb3QNCnRocm91Z2ggc29tZSBwYXRocy5zcQB+AAkAAXQAT1RoZSBsZXZlbCBvZiBhIG5vZGUgaXMgdGhlIGxlbmd0aCBvZiB0aGUgcGF0aCBmcm9tIHRoZSByb290IHRvIHRoZSBub2RlIHBsdXMgMS54dAAxV2hpY2ggb2YgdGhlIGZvbGxvd2luZyBjb25jZXB0cyBvZiB0cmVlIGFyZSB0cnVlOnNxAH4ABXNxAH4AAwAAAAR3BAAAAARzcQB+AAkAAXQAUVRoZSBjb21wbGV4aXR5IG9mIHNlYXJjaGluZyBhIG5vZGUgaXMgdGhlIGxlbmd0aCBvZiB0aGUgcGF0aCBsZWFkaW5nIHRvIHRoaXMgbm9kZXNxAH4ACQAAdAA4QSBzZWFyY2ggY2FuIHRha2VzIGxnICggbiApIHRpbWUgdW5pdHMgaW4gdGhlIHdvcnN0IGNhc2VzcQB+AAkAAHQAQUZvciBhIGJpbmFyeSB0cmVlIHdpdGggbiBub2RlcywgdGhlcmUgYXJlIG4hIGRpZmZlcmVudCB0cmF2ZXJzYWxzc3EAfgAJAAB0ADFCcmVhdGgtRmlyc3QgdHJhdmVyc2FsIGlzIGltcGxlbWVudGVkIHVzaW5nIHF1ZXVleHQAGFNlbGVjdCBjb3JyZWN0IHN0YXRlbWVudHNxAH4ABXNxAH4AAwAAAAR3BAAAAARzcQB+AAkAAHQAH1N0YWNrbGVzcyBEZXB0aC1GaXJzdCBUcmF2ZXJzYWxzcQB+AAkAAHQAEk5vbmUgb2YgdGhlIG90aGVyc3NxAH4ACQABdAAWQnJlYXRoLUZpcnN0IFRyYXZlcnNhbHNxAH4ACQAAdAAVRGVwdGgtRmlyc3QgVHJhdmVyc2FseHQAwi4uLi4uLi4uLiB3aWxsIHZpc2l0IG5vZGVzIG9mIGEgdHJlZSBzdGFydGluZyBmcm9tIHRoZSBoaWdoZXN0IChvciBsb3dlc3QpIGxldmVsIGFuZCBtb3ZpbmcgZG93biAob3IgdXApIGxldmVsIGJ5IGxldmVsIGFuZCBhdCBhIGxldmVsLCBpdCB2aXNpdHMgbm9kZXMgZnJvbSBsZWZ0IHRvIHJpZ2h0IChvciBmcm9tIHJpZ2h0IHRvIGxlZnQpc3EAfgAFc3EAfgADAAAABHcEAAAABHNxAH4ACQAAdAACMTVzcQB+AAkAAHQAAjMwc3EAfgAJAAF0AAIyMHNxAH4ACQAAdAACMTB4dACHQSBiYWxhbmNlZCBiaW5hcnkgc2VhcmNoIHRyZWUgY29udGFpbmluZyBvbmUgbWlsbGlvbiBub2Rlcy4gV2hhdCBpcyB0aGUgbWF4aW11bQ0KbnVtYmVyIG9mIGNvbXBhcmlzb25zIG5lZWRlZCB0byBmaW5kIGEga2V5IGluIHRoZSB0cmVleA==')
INSERT [dbo].[Subject] ([SubID], [SubCodeName], [SubName], [CreateDate], [Duration], [Status]) VALUES (N'1', N'CSD201', N'Data Structures And Algorithms', CAST(N'2021-01-02T00:00:00.000' AS DateTime), 3600000, 1)
INSERT [dbo].[Subject] ([SubID], [SubCodeName], [SubName], [CreateDate], [Duration], [Status]) VALUES (N'2', N'OSG202', N'Operating Systems', CAST(N'2021-02-02T00:00:00.000' AS DateTime), 4500000, 1)
INSERT [dbo].[Subject] ([SubID], [SubCodeName], [SubName], [CreateDate], [Duration], [Status]) VALUES (N'3', N'PRJ321', N'Web-Based Java Application', CAST(N'2021-02-02T00:00:00.000' AS DateTime), 9000000, 1)
INSERT [dbo].[Subject] ([SubID], [SubCodeName], [SubName], [CreateDate], [Duration], [Status]) VALUES (N'4', N'CEA201', N'Computer Organization And Architecture ', CAST(N'2021-01-01T00:00:00.000' AS DateTime), 3600000, 1)
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Subject__9E57E8983BD4CA5D]    Script Date: 2/3/2021 11:16:34 PM ******/
ALTER TABLE [dbo].[Subject] ADD UNIQUE NONCLUSTERED 
(
	[SubCodeName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Answer]  WITH CHECK ADD FOREIGN KEY([QuestID])
REFERENCES [dbo].[Question] ([QuestID])
GO
ALTER TABLE [dbo].[Question]  WITH CHECK ADD FOREIGN KEY([SubID])
REFERENCES [dbo].[Subject] ([SubID])
GO
ALTER TABLE [dbo].[ResultSubject]  WITH CHECK ADD  CONSTRAINT [FK__ResultSub__Email__33D4B598] FOREIGN KEY([Email])
REFERENCES [dbo].[Account] ([Email])
GO
ALTER TABLE [dbo].[ResultSubject] CHECK CONSTRAINT [FK__ResultSub__Email__33D4B598]
GO
ALTER TABLE [dbo].[ResultSubject]  WITH CHECK ADD  CONSTRAINT [FK__ResultSub__SubID__32E0915F] FOREIGN KEY([SubID])
REFERENCES [dbo].[Subject] ([SubID])
GO
ALTER TABLE [dbo].[ResultSubject] CHECK CONSTRAINT [FK__ResultSub__SubID__32E0915F]
GO
USE [master]
GO
ALTER DATABASE [QuizOnlineDB] SET  READ_WRITE 
GO
