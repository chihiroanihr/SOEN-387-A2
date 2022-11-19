<div id="formContainer" class="container px-3">
    <form id="registrationForm" action="" method="POST">
        <div class="mb-3">
            <label for="userID" class="form-label">User ID</label>
            <input type="text" class="form-control" id="userID" name="userID" placeholder="Enter User ID"/>
            <span id="invalidID" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <label for="userFirstName" class="form-label">First Name</label>
            <input type="text" class="form-control" id="userFirstName" name="userFirstName"
                   placeholder="Enter First Name"/>
            <span id="invalidFirstName" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <label for="userLastName" class="form-label">Last Name</label>
            <input type="text" class="form-control" id="userLastName" name="userLastName"
                   placeholder="Enter Last Name"/>
            <span id="invalidLastName" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <label for="userDOB" class="form-label">Date of Birth</label>
            <input type="date" class="form-control" id="userDOB" name="userDOB"/>
            <span id="invalidDOB" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <label for="userAddress" class="form-label">Address</label>
            <input type="text" class="form-control" id="userAddress" name="userAddress" placeholder="Enter Address"/>
            <span id="invalidAddress" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <label for="userCity" class="form-label">City</label>
            <input type="text" class="form-control" id="userCity" name="userCity" placeholder="Enter City"/>
            <span id="invalidCity" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <%@ include file="../helper/country-dropdown.html" %>
            <span id="invalidCountry" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <label for="userPostal" class="form-label">Postal Code</label>
            <input type="text" class="form-control" id="userPostal" name="userPostal" placeholder="Enter Postal Code"/>
            <span id="invalidPostal" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <label for="userEmail" class="form-label">Email Address</label>
            <input type="text" class="form-control" id="userEmail" name="userEmail" placeholder="Enter Email Address"/>
            <span id="invalidEmail" class="text-danger"></span>
        </div>

        <div class="mb-3">
            <label for="userPhone" class="form-label">Phone Number</label>
            <input type="text" class="form-control" id="userPhone" name="userPhone" placeholder="Enter Phone Number"/>
            <span id="invalidPhone" class="text-danger"></span>
        </div>

        <div class="row my-3 gx-5 gy-3 mx-4">
            <div class="col-sm-6">
                <!-- <div class="mt-4 mb-3 d-flex justify-content-around"> -->
                <input type="submit" id="registerButton" name="registerButton" class="btn btn-primary"
                       value="Register"/>
            </div>
            <div class="col-sm-6">
                <input type="reset" id="resetButton" class="btn btn-light" value="Reset"/>
            </div>
        </div>
    </form>
</div>