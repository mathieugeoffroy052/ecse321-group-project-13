<template>
  <div id="libraryservices">
    <h2>Library Services</h2>
    <div>
      <b-tabs content-class="mt-3" fill>
        <b-tab title="Patron Account" active>
          <b-container id="patronAccountLayout">
            <b-row>
              <b-col id="panne" class="shadow p-3 m-3 bg-white rounded">
                <h5>User Information</h5>
                <b-form-group>
                  <b-form-input
                    id="input-userID"
                    v-model="form.userID"
                    placeholder="Enter a UserID"
                    required
                  ></b-form-input>
                </b-form-group>
                <p class="my-3">Name:</p>
                <p class="my-3">Email:</p>
                <p class="my-3">Address: <b-button style="height:24px;width:83px" class="float-right py-0" id="tempButtons" variant="danger">Validate</b-button> </p>
                <p class="my-3">Balance: <b-button style="height:24px;width:83px" class="float-right py-0" id="tempButtons" variant="danger">Payed</b-button> </p>
              </b-col>
              <b-col class="shadow p-3 m-3 bg-white rounded"> 
                <h5>Transaction</h5>
                <b-form-select class="mb-2" v-model="selected" :options="optionsTransactionType"></b-form-select>
                <b-form-group>
                  <b-form-input
                    id="input-isbn"
                    v-model="form.isbn"
                    placeholder="Enter an ISBN"
                    required
                  ></b-form-input>
                </b-form-group>
                <p class="my-3">Type: Name</p>
                <p class="my-3">Author: Name</p>
                <p class="my-3">Return by: </p>
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
              >
                <b-form-input
                  id="input-4"
                  v-model="form.balance"
                  placeholder="Enter current account balance"
                  required
                ></b-form-input>
              </b-form-group>

              <div>
                <b-form-group
                  label="Online Account"
                  v-slot="{ ariaDescribedby }"
                >
                  <b-form-radio
                    v-model="selected"
                    :aria-describedby="ariaDescribedby"
                    name="some-radios"
                    value="Yes"
                    >Yes</b-form-radio
                  >
                  <b-form-radio
                    v-model="selected"
                    :aria-describedby="ariaDescribedby"
                    name="some-radios"
                    value="No"
                    >No</b-form-radio
                  >
                </b-form-group>
              </div>

              <b-form-checkbox
                id="checkbox-1"
                v-model="status"
                name="checkbox-1"
                value="accepted"
                unchecked-value="not_accepted"
              >
                Validated Account
              </b-form-checkbox>

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
