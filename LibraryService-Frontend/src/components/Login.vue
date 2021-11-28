<template>
  <div id="Login">
    <section class="vh-100" style="background-color: #FFFFFF;">
      <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
          <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div class="card shadow-2-strong" style="border-radius: 1rem;">
              <div class="card-body p-5 text-center">

                <h3 class="mb-5">Login</h3>

                <div class="form-outline mb-4">
                  <input type="userID" id="typeUserIDX-2" class="form-control form-control-lg" />
                  <label class="form-label" for="typeUserIDX-2">UserID</label>
                </div>

                <div class="form-outline mb-4">
                  <input type="password" id="typePasswordX-2" class="form-control form-control-lg" />
                  <label class="form-label" for="typePasswordX-2">Password</label>
                </div>

                <button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>

                <hr class="my-4">

                <p>Don't have an account? <a href="../#/CreateAccount" class="link-info">Create an account</a></p>

              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template> 
    <!-- <table>
      <tr>
          <td style="position:absolute; right:0px; left:0px;">
              <b-form-group id="input-group-1" label="UserID:" label-for="input-userID">
                <b-form-input
                  id="input-userID"
                  v-model="form.userID"
                  placeholder="Enter userID"
                  required
                ></b-form-input>

              <b-form-group id="input-group-2" label="Password:" label-for="input-password">
                <b-form-input
                  id="input-password"
                  v-model="form.password"
                  placeholder="Enter password"
                  required
                ></b-form-input>
              </b-form-group>
          </td>

          <td>
            <div style="position:absolute; right:0px; left:0px; top:350px">
              <b-button v-bind:disabled="!existingUser" @click="getUser(UserID)">Login</b-button>
              </div>
            <div style="position:absolute; right:0px; top:0px;">
              <b-button href='../#/CreateAccount'>Create Account</b-button>
              </div>
          </td>
      </tr>
    </table>
    <p style="position:absolute; top:300px; left:0px; right:0px;">
      <span v-if="errorPatron" style="color:red;">Error: {{errorPatron}}</span>
    </p>
 -->

<!--<script src="./login.js">
</script>
>--
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