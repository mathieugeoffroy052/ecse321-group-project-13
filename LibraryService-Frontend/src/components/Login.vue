 <template>
  <div id="Login" style="background-color: #97C3F9;">
    <section class="vh-100">
      <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
          <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <p v-if="noUsers">It appears the system has been reset, please initialize it:</p>
            <b-button v-if="noUsers" class="mb-5" variant="warning" @click="initializeSystem()">Initialize System</b-button>
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

                <b-button @click="login()" variant="primary">Login</b-button>

                <hr class="my-4">

                <p v-if="!noUsers">Don't have an account? <a href="../#/CreateAccount" class="link-info">Create an account</a></p>

              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template> 

<p style="position:absolute; top:300px; left:0px; right:0px;">
      <span v-if="errorPatron" style="color:red;">Error: {{errorPatron}}</span>
    </p>
<script src="./loginaccount.js">
</script>

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