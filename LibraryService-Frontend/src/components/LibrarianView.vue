<template>
  <div id="libraryservices">
    <h2>Library Services</h2>
    <div>
      <b-tabs content-class="mt-3" fill>
        <b-tab title="Patron Account" active>
          <b-container id="patronAccountLayout">
            <b-row>
              <b-col id="panne" class="shadow p-3 m-3 bg-white rounded">
                <h5>User Information
                  <b-button style="height:24px;width:24px" class="float-right p-0 d-inline" id="tempButtons" variant="success" @click="getPatron(input-user)">+</b-button> 
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
                  <p class="d-inline">Matty</p>
                </div>
                <div class="my-3">
                  <b>Email:</b>
                  <p class="d-inline">matty@gmail.com</p>
                </div>
                <div class="my-3">
                  <b>Address:</b>
                  <p class="d-inline">54 Durocher</p>
                  <b-button style="height:24px;width:83px" class="float-right py-0 d-inline" id="tempButtons" variant="danger">Validate</b-button> 
                </div>
                <div class="my-3">
                  <b>Balance:</b>
                  <p class="d-inline">$</p>
                  <p class="d-inline">5</p>
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
                    id="input-isbn"
                    v-model="form.isbn"
                    placeholder="Enter an ISBN"
                    required
                  ></b-form-input>
                </b-form-group>
                <div class="my-3">
                  <b>Type</b>
                  <b class="d-inline">Name:</b>
                  <p class="d-inline">Dora</p>
                </div>
                <div class="my-3">
                  <b>Author: </b>
                  <p class="d-inline">Diego</p>
                </div>
                <div class="my-3">
                  <b>Return by:</b>
                  <p class="d-inline">lol</p>
                </div>
              </b-col>
            </b-row>
            <b-row class="shadow p-3 my-3 mx-1 bg-white rounded">
              <h5>Previous Transactions</h5>
              <b-table hover :items="currentPatronTransactions"></b-table>
            </b-row>
          </b-container>
        </b-tab>
        <b-tab title="Add User">
          <div>
            <b-form @submit="onSubmit" @reset="onReset" v-if="true">
              <b-form-group
                id="input-group-1"
                label="First Name:"
                label-for="input-1"
                class="w-50"
              >
                <b-form-input
                  id="input-1"
                  v-model="form.name"
                  placeholder="Enter first name"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group
                id="input-group-2"
                label="Last Name:"
                label-for="input-2"
                class="w-50"
              >
                <b-form-input
                  id="input-2"
                  v-model="form.name"
                  placeholder="Enter last name"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group
                id="input-group-3"
                label="Address:"
                label-for="input-3"
                class="w-50"
              >
                <b-form-input
                  id="input-3"
                  v-model="form.address"
                  placeholder="Enter address"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group
                id="input-group-4"
                label="Balance:"
                label-for="input-4"
                class="w-50"
              >
                <b-form-input
                  id="input-4"
                  v-model="form.balance"
                  placeholder="Enter current account balance"
                  required
                ></b-form-input>
              </b-form-group>

              <div>
                <b-form-group label="Online Account" v-slot="{ ariaDescribedby }" >
                  <b-form-radio v-model="selected" :aria-describedby="ariaDescribedby" name="some-radios" id="online" value=true>Yes</b-form-radio>
                </b-form-group>
              </div>

              <b-form-group id="input-group-5" label="Password:" label-for="input-5" class="w-50">
                <b-form-input
                  id="input-5"
                  v-model="form.password"
                  placeholder="Enter password"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-6" label="Email:" label-for="input-6" class="w-50">
                <b-form-input
                  id="input-6"
                  v-model="form.email"
                  placeholder="Enter email"
                  required
                ></b-form-input>
              </b-form-group>

              <b-button type="Create" variant="primary">Submit</b-button>
              <b-button type="reset" variant="danger">Reset</b-button>
            </b-form>
          </div>
        </b-tab>
        <b-tab title="Workshifts"><p>I'm a disabled tab!</p></b-tab>
      </b-tabs>
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
</style>
