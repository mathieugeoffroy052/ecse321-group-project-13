<template>
  <div id="tester">
  <h2>Library</h2>
  <div>
    <b-tabs content-class="mt-3" fill>
      <b-tab title="Patron Account" active><p>I'm the first tab</p></b-tab>
      <b-tab title="Add User">
        <div>
            <b-form @submit="onSubmit" @reset="onReset" v-if=true>
              <b-form-group id="input-group-1" label="First Name:" label-for="input-1">
                <b-form-input
                  id="input-1"
                  v-model="form.name"
                  placeholder="Enter first name"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-2" label="Last Name:" label-for="input-2">
                <b-form-input
                  id="input-2"
                  v-model="form.name"
                  placeholder="Enter last name"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-3" label="Address:" label-for="input-3">
                <b-form-input
                  id="input-3"
                  v-model="form.address"
                  placeholder="Enter address"
                  required
                ></b-form-input>
              </b-form-group>

              <b-form-group id="input-group-4" label="Balance:" label-for="input-4">
                <b-form-input
                  id="input-4"
                  v-model="form.balance"
                  placeholder="Enter current account balance"
                  required
                ></b-form-input>
              </b-form-group>

              <div>
                <b-form-group label="Online Account" v-slot="{ ariaDescribedby }">
                  <b-form-radio v-model="selected" :aria-describedby="ariaDescribedby" name="some-radios" value="Yes">Yes</b-form-radio>
                  <b-form-radio v-model="selected" :aria-describedby="ariaDescribedby" name="some-radios" value="No">No</b-form-radio>
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

<script src="./librarianviewing.js">
</script>

<style>
  #libraryservices {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #97C3F9;
  }
</style>