<template>
  <div id="userAccount">
    <br>
    <h2>Your Account Information</h2>
    <b-container id="profileInfo">
      <b-row align-h="center">
        <b-col class="shadow p-3 m-3 bg-white rounded">
          <h5>Personal Information</h5>
          <b-avatar></b-avatar>
          <b-row>
            <b-col>
              <b-badge
                v-if="currentPatron.validatedAccount || isLibrarian"
                href="#"
                variant="success"
                >Validated</b-badge
              >
              <b-badge
                v-if="!currentPatron.validatedAccount && !isLibrarian"
                href="#"
                variant="danger"
                >Unvalidated</b-badge
              >
            </b-col>
          </b-row>
          <b-row class="my-3">
            <b-col>
              <p class="d-inline">{{ currentPatron.firstName }}</p>
              <p class="d-inline">{{ currentPatron.lastName }}</p>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <p>{{ currentPatron.email }}</p>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <p>{{ currentPatron.address }}</p>
            </b-col>
          </b-row>
        </b-col>
        <b-col class="shadow p-3 m-3 bg-white rounded">
          <div>
            <h5>Current Balance</h5>
            <p style="font-size:75px;" class="m-5">${{ currentPatron.balance }}</p>
          </div>
        </b-col>
      </b-row>

      <b-row class="shadow p-3 m-1 bg-white rounded">
        <b-col>
          <h5>Update Information</h5>
          <b-row>
            <b-col>
              <p>Email Address:</p>
              <b-form-input
                id="updateEmail"
                placeholder="New Email"
              ></b-form-input>
            </b-col>
            <b-col>
              <p>Change Password:</p>
              <b-form-input
                id="updatePassword"
                placeholder="New Password"
                type="password"
              ></b-form-input>
              <b-form-input
                id="updatePassword1"
                placeholder="Confirm Password"
                class="my-2"
                type="password"
              ></b-form-input>
            </b-col>
          </b-row>
          <b-row>
            <b-col>
              <b-button block variant="primary" @click="updateEmail()"
                >Confirm
              </b-button>
            </b-col>
            <b-col>
              <b-button block variant="primary" @click="updatePassword()"
                >Confirm
              </b-button>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row class="shadow p-3 my-3 mx-1 bg-white rounded">
        <b-col>
          <h5>Transactions</h5>
          <b-table hover :items="currentPatronTransactions"></b-table>
        </b-col>
      </b-row>
    </b-container>
    <br>
  </div>
</template>

<script src="./userInfoViewing.js"></script>

<style>
#userAccount{
   background-color: #97C3F9;
}
</style>
