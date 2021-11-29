<template>
    <div id="Create an Account">
        <h3 class="mb-5">Create an Account</h3>
            <b-container>
                <b-row class="shadow p-3 m-3 bg-white rounded">
                    <div class="w-100">
                        <b-form @submit="onSubmit" @reset="onReset" v-if="true">
                            <b-form-group
                                id="input-group-1"
                                label="First Name:"
                                label-for="input-firstName"
                                
                             ><b-form-input
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
                                                            v-model="form.password"
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
                                                            v-model="form.email"
                                                            placeholder="Enter email"
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
                                        <!--<div class="form-group col-md-6">
                                            <label for="inputFirstName4">First Name</label>
                                            <input type="FirstName" class="form-label" id="inputFirstName4" placeholder="Enter First Name">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="inputLastName4">Last Name</label>
                                            <input type="LastName" class="form-label" id="inputLastName4" placeholder="Enter Last Name">
                                        </div>
                                        <div class="mx-auto" style="width: 200px;">
                                            <div class="form-group">
                                                <label for="inputAddress">Address</label>
                                                <input type="text" class="form-label" id="inputAddress" placeholder="Enter Address">
                                            </div>
                                        </div>
                                        
                                        <div class="form-group col-md-6">
                                            <label for="inputEmail4">Email</label>
                                            <input type="Email" class="form-label" id="inputEmail4" placeholder="Enter Email">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="inputPassword4">Password</label>
                                            <input type="password" class="form-label" id="inputPassword4" placeholder="Create Password">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="inputPassword4">Confirm Password</label>
                                            <input type="password" class="form-label" id="inputPassword4" placeholder="Re-enter Password">
                                        </div> -->

                           

<script src="./createaccount.js"></script>

<style>
  #librarySystem {
    position: absolute;
    width: 1433px;
    height: 831px;

  }
</style>
<style>
  #libraryTitle {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;

    position: absolute;
    width: 540px;
    height: 98px;
    left: 50px;
    top: 59px;
  }
  </style>

  <style>
    #lightblueBox {
      position: absolute;
      width: 519px;
      height: 414px;
      left: 151px;
      top: 294px;

      background: rgba(255, 255, 255, 0.6);
      border: 1px solid #000000;
      box-sizing: border-box;
    }
  </style>

  <style>
    #whiteseperator {
      position: absolute;
      width: 674px;
      height: 866px;
      left: 766px;
      top: 0px;
    }
  </style>

  <style>
    #logoBook {
      position: absolute;
      width: 115.43px;
      height: 114.04px;
      left: calc(50% - 115.43px/2 - 641.26px);
      top: calc(50% - 114.04px/2 - 353.48px);

      transform: rotate(0.48deg);
    }
    </style>

<style>
#patronAccountLayout {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #ffffff;
  text-align: left;
}
</style>
  