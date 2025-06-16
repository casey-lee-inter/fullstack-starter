import Button from '@material-ui/core/Button'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import Grid from '@material-ui/core/Grid'
import { InputAdornment } from '@material-ui/core'
import { MeasurementUnits } from '../../constants/units'
import { MenuItem } from '@material-ui/core'
import React from 'react'
import TextField from '../Form/TextField'
import { Typography } from '@material-ui/core'
import { Field, Form, Formik } from 'formik'

class InventoryFormModal extends React.Component {
  render() {
    const {
      formName,
      handleDialog,
      handleInventory,
      title,
      initialValues,
      productMap,
      isDialogOpen
    } = this.props
    return (
      <Dialog
        open={isDialogOpen}
        maxWidth='sm'
        fullWidth={true}
        onClose={() => { handleDialog(false) }}
      >
        <Formik
          initialValues={initialValues}
          onSubmit={values => {
            handleInventory(values)
            handleDialog(true)
          }}
        >
          {helpers =>
            <Form
              noValidate
              autoComplete='off'
              id={formName}
            >
              <DialogTitle id='alert-dialog-title'>
                {`${title} Inventory`}
              </DialogTitle>
              <DialogContent>
                <Grid container spacing={1}>
                  <Grid item xs={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true,}}
                      name='name'
                      label='Name'
                      required
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='productType'
                      select
                      label='Product Type'
                      required
                      component={TextField}
                    >
                      {productMap.map((value, index) =>
                        <MenuItem key={index} value={value.name}>
                          {value.name}
                        </MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true,}}
                      name='description'
                      label='Description'
                      multiline
                      maxrows={3}
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{
                        variant: 'outlined',
                        fullWidth: true,
                        inputProps: {startAdornment: <InputAdornment position="start">$</InputAdornment>}
                      }}
                      name='averagePrice'
                      label='Average Price'
                      type='number'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{
                        variant: 'outlined',
                        fullWidth: true
                      }}
                      name='amount'
                      label='Amount'
                      type='number'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='unitOfMeasurement'
                      select
                      label='Unit of Measurement'
                      component={TextField}
                    >
                      {Object.entries(MeasurementUnits).map(([key, option]) =>
                        <MenuItem key={key} value={key}>
                          {option.name}
                        </MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true,}}
                      name='bestBeforeDate'
                      label='Best-Before'
                      type='date'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={4}>
                    <Typography variant="subtitle1">
                      Never Expires
                    </Typography>
                    <Field
                      name='neverExpires'
                      label='Never Expires'
                      type='checkbox'
                      sx={{
                        '& .MuiSvgIcon-root': { fontSize: 40 }
                      }}
                    />
                  </Grid>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button onClick={() => { handleDialog(false) }} color='secondary'>Cancel</Button>
                <Button
                  disableElevation
                  variant='contained'
                  type='submit'
                  form={formName}
                  color='secondary'
                  disabled={!helpers.dirty}>
                  Save
                </Button>
              </DialogActions>
            </Form>
          }
        </Formik>
      </Dialog>
    )
  }
}

export default InventoryFormModal