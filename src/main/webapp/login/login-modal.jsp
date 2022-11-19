<!-- Modal Login Popup Dialogue -->
<div class="modal fade" id="loginModal">
  <div class="modal-dialog modal-dialog-centered justify-content-center">
    <div id="loginFormWrapper" class="shadow-sm border rounded-3 bg-light">
      <img
        src="${pageContext.request.contextPath}/assets/images/school_logo.png"
        alt="School Logo"
        class="mx-auto d-block"
        width="250"
      />
      <form id="loginForm" name="loginForm" method="POST">
        <div>
          <span id="errorMessage" class="text-danger"></span>
        </div>
        <div class="mb-3">
          <input
            type="text"
            id="userID"
            name="userID"
            class="form-control input_user border"
            placeholder="Username"
          />
          <span id="invalidID" class="text-danger"></span>
        </div>
        <div class="mb-3">
          <input
            type="password"
            id="userPassword"
            name="userPassword"
            class="form-control input_pass border"
            placeholder="Password"
          />
          <span id="invalidPassword" class="text-danger"></span>
        </div>
        <p class="small"><a href="#">Forgot password?</a></p>
        <div class="d-grid">
          <input
            type="submit"
            id="loginButton"
            name="loginButton"
            class="btn"
            value="Log In"
          />
        </div>
      </form>
      <div class="mt-3">
        <p class="mb-0 text-center">
          Don't have an account?
          <a id="userRegister" href="#" class="fw-bold">Register</a>
        </p>
      </div>
    </div>
  </div>
</div>
