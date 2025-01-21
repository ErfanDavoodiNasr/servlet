<%@ page import="com.github.demoapp.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Information</title>
    <!-- Include Google Fonts -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap">
    <!-- Include FontAwesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Internal CSS -->
    <style>
        /* General Styles */
        body {
            font-family: 'Roboto', Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 900px;
            margin: 50px auto;
            background-color: #fff;
            padding: 30px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h2 {
            text-align: center;
            color: #444;
            margin-bottom: 30px;
        }

        .form-section {
            width: 100%;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        .form-group {
            width: 48%;
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: 500;
            color: #555;
            margin-bottom: 5px;
        }

        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            background-color: #fafafa;
            font-size: 14px;
            box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.07);
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        input:focus {
            border-color: #4caf50;
            box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1), 0 0 5px rgba(76, 175, 80, 0.3);
        }

        /* Profile Picture Section */
        .profile-picture-section {
            position: relative;
            width: 200px;
            height: 200px;
            margin-bottom: 20px;
            text-align: center;
        }

        .profile-img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            object-fit: cover;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .profile-img:hover {
            transform: scale(1.05);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        .profile-btn-container {
            position: absolute;
            bottom: 10px;
            right: 10px;
        }

        .profile-btn {
            background-image: linear-gradient(to right, #4caf50, #66bb6a);
            color: #fff;
            border: none;
            padding: 8px 16px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: background-image 0.3s ease;
        }

        /* Logout Button Styles */
        .logout-btn-container {
            margin-top: 20px;
            text-align: center;
        }

        .logout-btn {
            background-image: linear-gradient(to right, #f44336, #e53935);
            color: #fff;
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
            transition: background-image 0.3s ease, box-shadow 0.3s ease;
        }

        .logout-btn:hover {
            background-image: linear-gradient(to left, #f44336, #e53935);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .profile-btn:hover {
            background-image: linear-gradient(to left, #4caf50, #66bb6a);
        }

        .profile-menu {
            position: absolute;
            background-color: #fff;
            border: 1px solid #ccc;
            padding: 5px;
            display: none;
            z-index: 1;
            top: 30px;
            right: 0;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        .menu-item {
            display: block;
            text-decoration: none;
            color: #333;
            padding: 8px 12px;
            border-radius: 4px;
        }

        .menu-item:hover {
            background-color: #f0f0f0;
        }

        .hidden-input {
            display: none;
        }

        /* Submit Button */
        .submit-btn {
            background-image: linear-gradient(to right, #4caf50, #66bb6a);
            color: #fff;
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15);
            transition: background-image 0.3s ease, box-shadow 0.3s ease;
        }

        .submit-btn:hover {
            background-image: linear-gradient(to left, #4caf50, #66bb6a);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Edit User Information</h2>
    <form action="dashboard" method="post" enctype="multipart/form-data">
        <!-- Profile Picture Section -->
        <% User user = (User) session.getAttribute("user");%>
        <div class="profile-picture-section">
            <%
                String profile = user.getProfileImage();
                if (profile != null) {
            %>
            <img alt="Profile Picture" class="profile-img" id="profileImage" src="profile">
            <% } else { %>
            <img alt="Default Profile Picture" class="profile-img" id="profileImage" src="images.jpeg">
            <% } %>
            <input type="file" id="profilePictureInput" name="profile" accept="image/*">
        </div>

        <!-- Form Fields -->

        <div class="form-section">
            <!-- First Name -->
            <div class="form-group">
                <label for="FirstName">First Name</label>
                <input type="text" id="FirstName" name="firstName" value="<%= user.getFirstname()%>" required>
            </div>
            <!-- Last Name -->
            <div class="form-group">
                <label for="LastName">Last Name</label>
                <input type="text" id="LastName" name="lastName" value="<%= user.getLastName()%>" required>
            </div>
            <!-- Email -->
            <div class="form-group">
                <label for="Email">Email</label>
                <input type="email" id="Email" name="email" value="<%=user.getEmail()%>">
            </div>
            <!-- Password Fields -->
            <div class="form-group">
                <p><strong>Note:</strong> Leave password fields empty if you don't want to change your password.</p>
            </div>
            <div class="form-group">
                <label for="CurrentPassword">Current Password</label>
                <input type="password" id="CurrentPassword" name="CurrentPassword" placeholder="••••••••">
            </div>

            <div class="form-group">
                <label for="NewPassword">New Password</label>
                <input type="password" id="NewPassword" name="NewPassword" placeholder="••••••••">
            </div>
            <div class="form-group">
                <label for="RepeatNewPassword">Repeat New Password</label>
                <input type="password" id="RepeatNewPassword" name="RepeatNewPassword" placeholder="••••••••">
            </div>
            <!-- Submit Button -->
            <button type="submit" class="submit-btn">Save Changes</button>
        </div>
    </form>
    <div class="logout-btn-container">
        <form action="logout" method="post">
            <button type="submit" class="logout-btn">Log Out</button>
        </form>
    </div>
</div>

<script>
    <%
        if (request.getAttribute("message") != null) {
    %>
    alert('<%= request.getAttribute("message") %>');
    <% } %>
</script>

</body>
</html>