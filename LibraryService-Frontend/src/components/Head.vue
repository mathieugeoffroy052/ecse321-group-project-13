<template>
  <div id="headlibraryservices">
    <h2>Library Services</h2>
    <div>
      <b-tabs content-class="mt-3" fill>
        <b-tab title="Patron Account" active>
          <b-container id="patronAccountLayout">
            <b-row>
              <b-col id="panne" class="shadow p-3 m-3 bg-white rounded">
                <h5>User Information
                  <b-button style="height:24px;width:24px" class="float-right p-0 d-inline" id="tempButtons" variant="success" @click="getPatron()">+</b-button> 
                </h5>
                <b-form-group>
                  <b-form-input
                    id="input-userID"
                    v-model="form.userID"
                    placeholder="Enter a UserID"
                  ></b-form-input>
                </b-form-group>
                <div class="my-3">
                  <b>Name:</b>
                  <p class="d-inline">{{currentPatron.firstName}}</p>
                  <p class="d-inline">{{currentPatron.lastName}}</p>
                </div>
                <div class="my-3">
                  <b>Email:</b>
                  <p class="d-inline">{{currentPatron.email}}</p>
                </div>
                <div class="my-3">
                  <b>Address:</b>
                  <p class="d-inline">{{currentPatron.address}}</p>
                  <b-button style="height:24px;width:83px" class="float-right py-0 d-inline" id="tempButtons" variant="danger">Validate</b-button> 
                </div>
                <div class="my-3">
                  <b>Balance:</b>
                  <p class="d-inline">$</p>
                  <p class="d-inline">{{currentPatron.balance}}</p>
                  <b-button style="height:24px;width:83px" class="float-right py-0 d-inline" id="tempButtons" variant="danger">Payed</b-button>
                </div>
              </b-col>
              <b-col class="shadow p-3 m-3 bg-white rounded"> 
                <h5>Transaction
                  <b-button style="height:24px;width:24px" class="float-right p-0 d-inline" id="tempButtons" variant="success">+</b-button> 
                </h5>
                <b-form-select class="mb-2" v-model="selectedTransactionType" :options="optionsTransactionType"></b-form-select>
                <b-form-group>
                  <b-form-input
                    id="input-barcode"
                    v-model="form.barcode"
                    placeholder="Enter a barcode"
                    required
                  ></b-form-input>
                </b-form-group>
                <div class="my-3">
                  <b>{{borrowableItem.type}}</b>
                  <b class="d-inline">Name:</b>
                  <p class="d-inline">{{borrowableItem.name}}</p>
                </div>
                <div class="my-3">
                  <b>{{creator}}:</b>
                  <p class="d-inline">{{borrowableItem.creator}}</p>
                </div>
                <div class="my-3">
                  <b>Return by:</b>
                  <p class="d-inline">{{transaction.deadline}}</p>
                </div>
              </b-col>
            </b-row>
            <b-row class="shadow p-3 my-3 mx-1 bg-white rounded">
              <h5>Previous Transactions</h5>
              <b-table hover :items="currentPatronTransactions"></b-table>
            </b-row>
          </b-container>
        </b-tab>
        <b-tab title="Add New User">
          <h3>Create User</h3>
          <b-container>
            <b-row class="shadow p-3 m-3 bg-white rounded">
              <div class="w-100">
            <b-form @submit="onSubmit" @reset="onReset" v-if="true">
              <b-form-select class="mb-2" v-model="selectedUser" :options="optionsUsers"></b-form-select>
              <b-form-group
                id="input-group-1"
                label="First Name:"
                label-for="input-firstName"
              >
                <b-form-input
                  id="input-firstName"
                  v-model="form.firstName"
                  placeholder="Enter first name"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group
                id="input-group-2"
                label="Last Name:"
                label-for="input-lastName"
              >
                <b-form-input
                  id="input-lastName"
                  v-model="form.lastName"
                  placeholder="Enter last name"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group
                id="input-group-3"
                label="Address:"
                label-for="input-address"
              >
                <b-form-input
                  id="input-address"
                  v-model="form.address"
                  placeholder="Enter address"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group
                id="input-group-4"
                label="Balance:"
                label-for="input-balance"
              >
                <b-form-input
                  id="input-balance"
                  v-model="form.balance"
                  placeholder="Enter current account balance"
                  required
                ></b-form-input>
              </b-form-group>

              <div class="form-check p-3">
                <input class="form-check-input" type="checkbox" value=false id="input-onlineAccount">
                <label class="form-check-label" for="input-onlineAccount">
                  Online Account
                </label>
              </div>

              <b-form-group id="input-group-5" label="Password:" label-for="input-password">
                <b-form-input
                  id="input-password"
                  v-model="form.password"
                  placeholder="Enter password"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-6" label="Email:" label-for="input-email" >
                <b-form-input
                  id="input-email"
                  v-model="form.email"
                  placeholder="Enter email"
                  required
                ></b-form-input>
              </b-form-group>


              <b-row class="justify-content-center">
                <b-col class="p-3 ">
                  <b-button type="Create" class="float-right" variant="primary" style="width:76px;height:38">Submit</b-button>
                </b-col>
                <b-col class="p-3">
                  <b-button type="reset" class="float-left" variant="danger" style="width:76px;height:38">Reset</b-button>
                </b-col>
              </b-row>

            </b-form>
              </div>
            </b-row>
          </b-container>
        </b-tab>
        <b-tab title="Workshifts" @click="getShifts">
        <h3>Work Schedule</h3>
        <b-container>
        <b-row class="shadow p-3 m-3 bg-white rounded">
            <div class="w-100">
            <b-table hover :items="currentShift"></b-table>
            </div>
        </b-row>
        </b-container>
        </b-tab>
        <b-tab title="Library Information" @click="getShifts">

        </b-tab>
        <b-tab title="Staff" @click="getAllStaff"><p>
          <h3>Staff</h3>
            <b-container>
                <b-row>
                   <b-col class="shadow p-3 m-3 bg-white rounded">
                        <b-row>
                          <div class="w-100">
                            <b-table hover :items="currentStaff"></b-table>
                          </div>
                        </b-row>
                        <b-form @submit="onSubmit" @reset="onReset" v-if="true">
                            <b-row class="justify-content-center">
                                <b-form-group
                                    id="input-userID"
                                    label="To delete a specific user, enter their ID below:"
                                    label-for="input-userID">
                                    <b-form-input
                                    id="input-userID"
                                    v-model="form.userID"
                                    placeholder="Enter user ID"
                                    required
                                    ></b-form-input>
                                </b-form-group>
                            </b-row>
                            <b-row class="justify-content-center">
                                <b-col class="p-3 ">
                                <b-button type="Delete" class="float-right" variant="primary" style="width:76px;height:38">Delete</b-button>
                                </b-col>
                                <b-col class="p-3">
                                <b-button type="Cancel" class="float-left" variant="danger" style="width:76px;height:38">Cancel</b-button>
                                </b-col>
                            </b-row>
                        </b-form>
                    </b-col>
                    <b-col class="shadow p-3 m-3 bg-white rounded">
                        <h4> Assign workshift </h4>
                        <b-form-datepicker
                          id="workshift-datepicker"
                          v-model="dateWorkshift"
                          class="mb-2 mt-0"
                        ></b-form-datepicker>
                        <b-form-timepicker
                          id="workshift-startTimePicker"
                          v-model="startTimeWorkshift"
                          class="my-2"
                        ></b-form-timepicker>
                        <b-form-timepicker
                          id="workshift-endTimeWorkshift"
                          v-model="endTimeWorkshift"
                          class="my-2"
                        ></b-form-timepicker>
                        <b-form-group
                                  id="input-user"
                                  label="Enter the userID fot the selected librarian:"
                                  label-for="input-user">
                              <b-form-input
                                  id="input-user"
                                  v-model="form.user"
                                  placeholder="Enter a userID"
                                  required
                              ></b-form-input>
                              </b-form-group>
                        <b-row class="shadow p-3 m-3 bg-white rounded">
                            <div class="w-100">
                            <b-table hover :items="allShifts"></b-table>
                            </div>
                        </b-row>
                    </b-col>
                </b-row>
            </b-container>
        </b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<script src="./headlibrarian.js"></script>

<style>
#headlibraryservices {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #ffffff;
  text-align: center;
}
#patronAccountLayout {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #ffffff;
  text-align: left;
}
#panne {
  background: #dbdbdb;
}
</style>
