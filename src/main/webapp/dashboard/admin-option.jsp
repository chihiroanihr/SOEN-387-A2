<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<ul class="list-unstyled components flex-fill">
  <li>
    <a href="#">My work schedule</a>
  </li>
  <li class="active">
    <a href="#homeSubmenu1" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Courses</a>
    <ul class="collapse list-unstyled" id="homeSubmenu1">
      <li>
        <a href="${pageContext.request.contextPath}/admin_course/CreateCourse.jsp">Create course</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin_course/EditCourse.jsp">Edit course</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin_course/DeleteCourse.jsp">Delete course</a>
      </li>
    </ul>
  </li>
  <li class="active">
    <a href="#homeSubmenu2" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Get a
      Report</a>
    <ul class="collapse list-unstyled" id="homeSubmenu2">
      <li>
        <a href="${pageContext.request.contextPath}/admin_report/GetStudentListByCourse.jsp">List of students enrolled in a certain course</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin_report/GetCourseListByStudent.jsp">List of courses taken by a certain student</a>
      </li>
    </ul>
  </li>
  <li class="active">
    <a href="#homeSubmenu3" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Account</a>
    <ul class="collapse list-unstyled" id="homeSubmenu3">
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