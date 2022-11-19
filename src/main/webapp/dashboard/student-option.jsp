<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<ul class="list-unstyled components flex-fill">
    <li class="active">
        <a href="#homeSubmenu1" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Enrollment</a>
        <ul class="collapse list-unstyled" id="homeSubmenu1">
            <li>
                <a href="#">My class schedule</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/student_course/AddCourse.jsp">Add classes</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/student_course/DropCourse.jsp">Drop classes</a>
            </li>
        </ul>
    </li>
    <li>
        <a href="#">Finance</a>
    </li>
    <li class="active">
        <a href="#homeSubmenu2" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Account</a>
        <ul class="collapse list-unstyled" id="homeSubmenu2">
            <li>
                <a href="#">Profile</a>
            </li>
            <li>
                <a href="#">Settings</a>
            </li>
            <li>
                <a href="../logout.jsp">Sign Out</a>
            </li>
        </ul>
    </li>
</ul>
