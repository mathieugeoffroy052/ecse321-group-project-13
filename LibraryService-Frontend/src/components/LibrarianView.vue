<template>
  <div id="libraryservices">
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
                  v-model="form.userID"
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
                  v-model="form.barcode"
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
        <b-tab title="Add New Patron">
          <h3>Create Patron</h3>
          <b-container>
            <b-row class="shadow p-3 m-3 bg-white rounded">
              <div class="w-100">
                <b-form @submit="onSubmit" @reset="onReset" v-if="true">
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
                    <input
                      class="form-check-input"
                      type="checkbox"
                      value="false"
                      v-model="form.onlineAccount"
                    />
                    <label class="form-check-label" for="input-onlineAccount">
                      Online Account
                    </label>
                  </div>

                  <b-form-group
                    id="input-group-5"
                    label="Password:"
                    label-for="input-password"
                    v-if="form.onlineAccount"
                  >
                    <b-form-input
                      id="input-password"
                      v-model="form.password"
                      placeholder="Enter password"
                      type="password"
                      v-if="form.onlineAccount"
                    ></b-form-input>
                  </b-form-group>

                  <b-form-group
                    id="input-group-6"
                    label="Email:"
                    label-for="input-email"
                    v-if="form.onlineAccount"
                  >
                    <b-form-input
                      id="input-email"
                      v-model="form.email"
                      placeholder="Enter email"
                      v-if="form.onlineAccount"
                    ></b-form-input>
                  </b-form-group>

                  <b-row class="justify-content-center">
                    <b-col class="p-3 ">
                      <b-button
                        type="Create"
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
        <b-tab title="Workshifts" @click="getShifts"
          >
          <h3>Work Schedule</h3>
          <b-container>
            <b-row class="shadow p-3 m-3 bg-white rounded">
              <div class="w-100">
                <b-table hover :items="currentShift"></b-table>
              </div>
            </b-row>
          </b-container>
        </b-tab>
      </b-tabs>
      <br>
    </div>
  </div>
</template>

<script src="./librarianviewing.js"></script>

<style>
#libraryservices {
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
.tabs .nav-link.active {
  color: #2c3e50;
  background-color: #97C3F9 !important;
}
.tabs .nav-link {
  color: #2c3e50;
  
}
</style>
