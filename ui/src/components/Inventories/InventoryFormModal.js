import * as productDuck from '../../ducks/products'
import Button from '@material-ui/core/Button'
import { Checkbox } from '@material-ui/core'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import { FormControlLabel } from '@material-ui/core'
import { FormGroup } from '@material-ui/core'
import Grid from '@material-ui/core/Grid'
import { InputAdornment } from '@material-ui/core'
import { MenuItem } from '@material-ui/core'
import React from 'react'
import TextField from '../Form/TextField'
import { Typography } from '@material-ui/core'
import { Field, Form, Formik } from 'formik'


class InventoryFormModal extends React.Component {
  render() {
    const now = new Date()
    const tdate = now.toISOString().split('T')[0]
    const formatDateOnly = (date) => {
      if (!date) return ''
      const dateIn = new Date(date)
      return dateIn.toISOString().split('T')[0]
    }
    const units = [
      {
        value: 'cup',
        label: 'CUP'
      },
      {
        value: 'gallon',
        label: 'GALLON'
      },
      {
        value: 'ounces',
        label: 'OUNCE'
      },
      {
        value: 'pints',
        label: 'PINT'
      },
      {
        value: 'pounds',
        label: 'POUND'
      },
      {
        value: 'quarts',
        label: 'QUART'
      },
    ]
    const {
      formName,
      handleDialog,
      handleInventory,
      title,
      initialValues,
      productMap
    } = this.props
    return (
      <Dialog
        open={this.props.isDialogOpen}
        maxWidth='sm'
        fullWidth={true}
        onClose={() => { handleDialog(false) }}
      >
        <Formik
          initialValues={{
            ...initialValues,
            averagePrice: initialValues?.averagePrice ? initialValues.averagePrice : 0,
            amount: initialValues?.amount ? initialValues.amount : 0,
            bestBeforeDate: initialValues?.bestBeforeDate ? initialValues.bestBeforeDate.split('T')[0] : tdate,
          }}
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
                  {/*Name Field*/}
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true,}}
                      name='name'
                      label='Name'
                      required='required'
                      component={TextField}
                    />
                  </Grid>
                  {/*Product Type Dropdown*/}
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='productType'
                      select
                      label='Product Type'
                      required='required'
                      defaultValue=''
                      component={TextField}
                    >
                      {productMap.map((value, index) =>
                        <MenuItem key={index} value={value.name}>
                          {value.name}
                        </MenuItem>
                      )}
                    </Field>
                  </Grid>
                  {/*Description Field*/}
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true,}}
                      name='description'
                      label='Description'
                      multiline
                      maxrows={3}
                      component={TextField}
                    />
                  </Grid>
                  {/*Average Price number input*/}
                  <Grid item xs={6} sm={6}>
                    <Field
                      custom={{
                        variant: 'outlined',
                        fullWidth: true,
                        inputProps: {startAdornment: <InputAdornment position="start">$</InputAdornment>}
                      }}
                      name='averagePrice'
                      label='Average Price'
                      type='number'
                      defaultValue={0}
                      component={TextField}
                    />
                  </Grid>
                  {/*Amount number input */}
                  <Grid item xs={6} sm={6}>
                    <Field
                      custom={{
                        variant: 'outlined',
                        fullWidth: true
                      }}
                      name='amount'
                      label='Amount'
                      type='number'
                      defaultValue={0}
                      component={TextField}
                    />
                  </Grid>
                  {/*Unit of Measurement Dropdown*/}
                  <Grid item xs={6} sm={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='unitOfMeasurement'
                      select
                      label='Unit of Measurement'
                      component={TextField}
                      defaultValue=''
                    >
                      {units.map((option) =>
                        <MenuItem key={option.value} value={option.label}>
                          {option.value}
                        </MenuItem>
                      )}
                    </Field>
                  </Grid>
                  {/*Best Before Date text input*/}
                  <Grid item xs={6} sm={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true,}}
                      name='bestBeforeDate'
                      label='Best-Before'
                      type='date'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={4} sm={4}>
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