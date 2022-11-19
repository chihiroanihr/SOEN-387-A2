<div id="content" class="p-3">
    <div class='container-fluid'>

        <!-- Dashboard content title -->
        <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
            <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>
        </div>

        <!-- Content Row 1: Admin Profile -->
        <div class="row">
            <div class="col">
                <div class="card shadow mb-4 px-3 py-4">
                    <div class="text-center">
                        <i class="fa-solid fa-user-graduate fa-7x pb-4"></i>
                        <p id="profile-name">${userName} (${userId})</p>
                        <span class="profile-detail">${userEmail}</span> | <span class="pro-detail" id="number">${userPhone}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Content Row 2: Work schedule and Enrollment Dates-->
        <div class="row">
            <!-- Work Schedule -->
            <div class="col-lg-8 col-sm-12">
                <div class="card shadow mb-4">
                    <!-- Card Header - Dropdown -->
                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary">This week's schedule</h6>
                    </div>
                    <!-- Card Body -->
                    <div class="card-body">
                        <!--TODO: Weekly Table -->
                    </div>
                </div>
            </div>

            <!-- Enrollment Dates -->
            <div class="col-lg-4 col-sm-12 d-flex">
                <div class="card shadow mb-4 flex-fill">
                    <!-- Card Header - Dropdown -->
                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary">Enrollment Dates</h6>
                    </div>
                    <!-- Card Body -->
                    <div class="card-body">
                        <!-- ADD HERE -->
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>