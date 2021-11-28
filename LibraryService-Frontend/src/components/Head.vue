<template>
  <div id="headlibraryservices">
    <h2>Library Services</h2>
    <div>
      <b-tabs content-class="mt-3" fill>
        <b-tab title="Patron Account" active>
          <b-container id="patronAccountLayout">
            <b-row>
              <b-col id="panne" class="shadow p-3 m-3 bg-white rounded">
                <h5>
                  User Information
                  <b-button
                    style="height:24px;width:24px"
                    class="float-right p-0 d-inline"
                    id="tempButtons"
                    variant="primary"
                    @click="loadPatronInfo()"
                    >+</b-button
                  >
                </h5>
                <b-form-input
                  id="input-userID"
                  v-model="formUser.userID"
                  placeholder="Enter a UserID"
                ></b-form-input>
                <div class="my-3">
                  <b>Name:</b>
                  <p class="d-inline">{{ currentPatron.firstName }}</p>
                  <p class="d-inline">{{ currentPatron.lastName }}</p>
                </div>
                <div class="my-3">
                  <b>Email:</b>
                  <p class="d-inline">{{ currentPatron.email }}</p>
                </div>
                <div class="my-3">
                  <b>Address:</b>
                  <p class="d-inline">{{ currentPatron.address }}</p>
                  <b-button
                    style="height:24px;width:83px"
                    class="float-right py-0 d-inline"
                    id="tempButtons"
                    variant="danger"
                    v-if="needsValidation()"
                    @click="validateCurrentPatron()"
                    >Validate</b-button
                  >
                </div>
                <div class="my-3">
                  <b>Balance:</b>
                  <p class="d-inline" v-if="currentPatron != ''">$</p>
                  <p class="d-inline">{{ currentPatron.balance }}</p>
                  <b-button
                    style="height:24px;width:83px"
                    class="float-right py-0 d-inline"
                    id="tempButtons"
                    variant="danger"
                    @click="resetBalance()"
                    v-if="currentPatron.balance > 0"
                    >Payed</b-button
                  >
                </div>
              </b-col>
              <b-col class="shadow p-3 m-3 bg-white rounded">
                <h5>
                  Transaction
                  <b-button
                    style="height:24px;width:24px"
                    class="float-right p-0 d-inline"
                    id="tempButtons"
                    variant="primary"
                    @click="newTransaction()"
                    >+</b-button
                  >
                </h5>
                <b-form-select
                  id="input-transactiontype"
                  class="mb-2"
                  v-model="selectedTransactionType"
                  :options="optionsTransactionType"
                ></b-form-select>
                <b-form-input
                  id="input-barcode"
                  v-model="formCode.barcode"
                  placeholder="Enter a barcode"
                  class="mb-2"
                  v-if="!isReservingRoom()"
                  required
                ></b-form-input>
                <div class="my-3" v-if="!isReservingRoom()">
                  <b>{{ libraryItem.type }}</b>
                  <b class="d-inline">Name:</b>
                  <p class="d-inline">{{ libraryItem.name }}</p>
                </div>
                <div class="my-3" v-if="!isReservingRoom()">
                  <b>Creator:</b>
                  <p class="d-inline">{{ libraryItem.creator }}</p>
                </div>
                <div class="my-3" v-if="!isReservingRoom()">
                  <b>Return by:</b>
                  <p class="d-inline">{{ transaction.deadline }}</p>
                </div>
                <div v-if="isReservingRoom()">
                  <b-form-datepicker
                    id="room-reserve-datepicker"
                    v-model="dateRoomReserve"
                    class="mb-2 mt-0"
                  ></b-form-datepicker>
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
          <h3>Create New User</h3>
          <b-container>
            <b-row class="shadow p-3 m-3 bg-white rounded">
              <div class="w-100">
                <b-form @submit="onSubmitUSER" @reset="onResetUSER" v-if="true">
                  <b-form-select
                    class="mb-2"
                    v-model="selectedUser"
                    :options="optionsUsers"
                  ></b-form-select>
                  <b-form-group
                    id="input-group-1"
                    label="First Name:"
                    label-for="input-firstName"
                  >
                    <b-form-input
                      id="input-firstName"
                      v-model="formUser.firstName"
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
                      v-model="formUser.lastName"
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
                      v-model="formUser.address"
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
                      v-model="formUser.balance"
                      placeholder="Enter current account balance"
                      required
                    ></b-form-input>
                  </b-form-group>

                  <div class="form-check p-3">
                    <input
                      class="form-check-input"
                      type="checkbox"
                      value="false"
                      id="input-onlineAccount"
                    />
                    <label class="form-check-label" for="input-onlineAccount">
                      Online Account
                    </label>
                  </div>

                  <b-form-group
                    id="input-group-5"
                    label="Password:"
                    label-for="input-password"
                  >
                    <b-form-input
                      id="input-password"
                      v-model="formUser.password"
                      placeholder="Enter password"
                      required
                    ></b-form-input>
                  </b-form-group>

                  <b-form-group
                    id="input-group-6"
                    label="Email:"
                    label-for="input-email"
                  >
                    <b-form-input
                      id="input-email"
                      v-model="formUser.email"
                      placeholder="Enter email"
                      required
                    ></b-form-input>
                  </b-form-group>

                  <b-row class="justify-content-center">
                    <b-col class="p-3 ">
                      <b-button
                        type="submit"
                        class="float-right"
                        variant="primary"
                        style="width:76px;height:38"
                        >Submit</b-button
                      >
                    </b-col>
                    <b-col class="p-3">
                      <b-button
                        type="reset"
                        class="float-left"
                        variant="danger"
                        style="width:76px;height:38"
                        >Reset</b-button
                      >
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
        <b-tab
          title="Library Information"
          @click="
            getAllOpeningHours();
            getAllHolidays();
          "
        >
          <b-container>
            <b-row>
              <b-col>
                <b-col class="shadow p-3 m-3 bg-white rounded">
                  <h5>Create Opening Hours</h5>
                  <b-form
                    @submit="onSubmitHour"
                    @reset="onResetHour"
                    v-if="true"
                  >
                    <b-form-select
                      class="mb-2"
                      v-model="selectedDay"
                      :options="optionsDays"
                    ></b-form-select>
                    <b-form-timepicker
                      id="openingHours-startTimePicker"
                      v-model="startTimeOpeningHour"
                      class="my-2"
                    ></b-form-timepicker>
                    <b-form-timepicker
                      id="openingHours-endTimeWorkshift"
                      v-model="endTimeOpeningHour"
                      class="my-2"
                    ></b-form-timepicker>
                    <b-row class="justify-content-center">
                      <b-col class="p-3 ">
                        <b-button
                          type="submit"
                          class="float-right"
                          variant="primary"
                          style="width:76px;height:38"
                          >Create</b-button
                        >
                      </b-col>
                      <b-col class="p-3">
                        <b-button
                          type="reset"
                          class="float-left"
                          variant="danger"
                          style="width:76px;height:38"
                          >Reset</b-button
                        >
                      </b-col>
                    </b-row>
                  </b-form>
                  <h5>All OpeningHours</h5>
                  <b-row class="shadow p-3 m-3 bg-white rounded">
                    <div class="w-100">
                      <b-table hover :items="allOpeningHours"></b-table>
                    </div>
                  </b-row>
                  <h5>Delete an Opening Hour</h5>
                  <b-form
                    @submit="onSubmitDelHour"
                    @reset="onResetDelOpening"
                    v-if="true"
                  >
                    <b-row class="justify-content-center">
                      <b-form-group
                        id="input-OpeningHour"
                        label="To delete a specific opening hour, enter its ID below:"
                        label-for="input-OpeningHour"
                      >
                        <b-form-input
                          id="input-OpeningHour"
                          v-model="formOpeningHour.openingHourID"
                          placeholder="Enter OpeningHour ID"
                          required
                        ></b-form-input>
                      </b-form-group>
                    </b-row>
                    <b-row class="justify-content-center">
                      <b-col class="p-3 ">
                        <b-button
                          type="submit"
                          class="float-right"
                          variant="primary"
                          style="width:76px;height:38"
                          >Delete</b-button
                        >
                      </b-col>
                      <b-col class="p-3">
                        <b-button
                          type="reset"
                          class="float-left"
                          variant="danger"
                          style="width:76px;height:38"
                          >Cancel</b-button
                        >
                      </b-col>
                    </b-row>
                  </b-form>
                </b-col>
              </b-col>
              <b-col>
                <b-col class="shadow p-3 m-3 bg-white rounded">
                  <h5>Create a Holiday</h5>
                  <b-form
                    @submit="onSubmitHoliday"
                    @reset="onResetHoliday"
                    v-if="true"
                  >
                    <b-form-datepicker
                      id="Holiday-datepicker"
                      v-model="dateHoliday"
                      class="mb-2 mt-0"
                    ></b-form-datepicker>
                    <b-form-timepicker
                      id="Holiday-startTimePicker"
                      v-model="startTimeHoliday"
                      class="my-2"
                    ></b-form-timepicker>
                    <b-form-timepicker
                      id="Holiday-endTimePicker"
                      v-model="endTimeHoliday"
                      class="my-2"
                    ></b-form-timepicker>
                    <b-row class="justify-content-center">
                      <b-col class="p-3 ">
                        <b-button
                          type="submit"
                          class="float-right"
                          variant="primary"
                          style="width:76px;height:38"
                          >Create</b-button
                        >
                      </b-col>
                      <b-col class="p-3">
                        <b-button
                          type="reset"
                          class="float-left"
                          variant="danger"
                          style="width:76px;height:38"
                          >Reset</b-button
                        >
                      </b-col>
                    </b-row>
                  </b-form>
                  <h5>All Holidays</h5>
                  <b-row class="shadow p-3 m-3 bg-white rounded">
                    <div class="w-100">
                      <b-table hover :items="allHolidays"></b-table>
                    </div>
                  </b-row>
                  <h5>Delete a Holiday</h5>
                  <b-form
                    @submit="onSubmitDelHoliday"
                    @reset="onResetDelHoliday"
                    v-if="true"
                  >
                    <b-row class="justify-content-center">
                      <b-form-group
                        label="To delete a specific holiday, enter its ID below:"
                        label-for="input-Holiday"
                      >
                        <b-form-input
                          id="input-Holiday"
                          v-model="formHoliday.holiday"
                          placeholder="Enter Holiday ID"
                          required
                        ></b-form-input>
                      </b-form-group>
                    </b-row>
                    <b-row class="justify-content-center">
                      <b-col class="p-3 ">
                        <b-button
                          type="submit"
                          class="float-right"
                          variant="primary"
                          style="width:76px;height:38"
                          >Delete</b-button
                        >
                      </b-col>
                      <b-col class="p-3">
                        <b-button
                          type="reset"
                          class="float-left"
                          variant="danger"
                          style="width:76px;height:38"
                          >Cancel</b-button
                        >
                      </b-col>
                    </b-row>
                  </b-form>
                </b-col>
              </b-col>
            </b-row>
          </b-container>
        </b-tab>
        <b-tab
          title="Staff"
          @click="
            getAllStaff();
            getAllShifts();
          "
          ><p></p>
          <b-container>
            <b-row>
              <b-col class="shadow p-3 m-3 bg-white rounded">
                <h5>Current Staff</h5>
                <b-row class="shadow p-3 m-3 bg-white rounded">
                  <div class="w-100">
                    <b-table hover :items="currentStaff"></b-table>
                  </div>
                </b-row>
                <b-form @submit="onDelStaff" @reset="onResetStaff" v-if="true">
                  <b-row class="justify-content-center">
                    <b-form-group
                      id="input-userID"
                      label="To delete a specific user, enter their ID below:"
                      label-for="input-userID"
                    >
                      <b-form-input
                        id="input-userID-toDelete"
                        v-model="formStaff.userID"
                        placeholder="Enter user ID"
                        required
                      ></b-form-input>
                    </b-form-group>
                  </b-row>
                  <b-row class="justify-content-center">
                    <b-col class="p-3 ">
                      <b-button
                        type="submit"
                        class="float-right"
                        variant="primary"
                        style="width:76px;height:38"
                        >Delete</b-button
                      >
                    </b-col>
                    <b-col class="p-3">
                      <b-button
                        type="reset"
                        class="float-left"
                        variant="danger"
                        style="width:76px;height:38"
                        >Cancel</b-button
                      >
                    </b-col>
                  </b-row>
                </b-form>
              </b-col>
              <b-col class="shadow p-3 m-3 bg-white rounded">
                <h5>Assign workshift</h5>
                <b-form
                  @submit="onSubmitTimeslot"
                  @reset="onResetTimeslot"
                  v-if="true"
                >
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
                    label="Enter the User ID for the Selected Librarian:"
                    label-for="input-user"
                  >
                    <b-form-input
                      id="input-user"
                      v-model="formStaff.userID"
                      placeholder="Enter a userID"
                      required
                    ></b-form-input>
                  </b-form-group>
                  <b-row class="justify-content-center">
                    <b-col class="p-3 ">
                      <b-button
                        type="submit"
                        class="float-right"
                        variant="primary"
                        style="width:76px;height:38"
                        >Create</b-button
                      >
                    </b-col>
                    <b-col class="p-3">
                      <b-button
                        type="reset"
                        class="float-left"
                        variant="danger"
                        style="width:76px;height:38"
                        >Reset</b-button
                      >
                    </b-col>
                  </b-row>
                </b-form>
                <h5>All Workshifts</h5>
                <b-row class="shadow p-3 m-3 bg-white rounded">
                  <div class="w-100">
                    <b-table hover :items="allShifts"></b-table>
                  </div>
                </b-row>
                <h5>Delete a Workshift</h5>
                <b-form
                  @submit="onSubmitDelTimeslot"
                  @reset="onResetDelTimeslot"
                  v-if="true"
                >
                  <b-row class="justify-content-center">
                    <b-form-group
                      label="To delete a specific workshift, enter its ID below:"
                      label-for="input-timeslot"
                    >
                      <b-form-input
                        id="input-timeslot"
                        v-model="formHoliday.holiday"
                        placeholder="Enter Workshift ID"
                        required
                      ></b-form-input>
                    </b-form-group>
                  </b-row>
                  <b-row class="justify-content-center">
                    <b-col class="p-3 ">
                      <b-button
                        type="submit"
                        class="float-right"
                        variant="primary"
                        style="width:76px;height:38"
                        >Delete</b-button
                      >
                    </b-col>
                    <b-col class="p-3">
                      <b-button
                        type="reset"
                        class="float-left"
                        variant="danger"
                        style="width:76px;height:38"
                        >Cancel</b-button
                      >
                    </b-col>
                  </b-row>
                </b-form>
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
