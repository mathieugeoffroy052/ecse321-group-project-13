<template>
    <div id="CreateAccount">
    <h3>Create Account</h3>
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
                    id="input-group-6"
                    label="Email:"
                    label-for="input-email"
                  >
                    <b-form-input
                      id="input-email"
                      v-model="form.email"
                      placeholder="Enter email"
                      required
                    ></b-form-input>
                  </b-form-group>

                  <b-form-group
                    id="input-group-5"
                    label="Password:"
                    label-for="input-password"
                  >
                    <b-form-input
                      id="input-password"
                      v-model="form.password"
                      placeholder="Enter password"
                      required
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
        </div>
</template>

<script src="./createuseraccount.js"></script>

<style>
#libraryservices {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #ffffff;
  text-align: center;
}
#createAccountLayout {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #ffffff;
  text-align: left;
}
#panne {
  background: #dbdbdb;
}
</style>
