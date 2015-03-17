# Stock Option Vesting

As part of a robust compensation package, employees are given the option to buy stock (a “stock option”)
at a favorable set price (the “grant” or “strike” price) after a certain period of time has passed (the “vesting
period”). The date that the options are granted is known as the “grant date,” and the date the options are
available is known as the “vest date.”

Given a list of records outlining vest dates and amounts, calculate the total gain an employee has at a given
date and market price.

**Note:** For any required rounding, use “round half up” behavior – round towards “nearest neighbor” unless
both neighbors are equidistant, in which case round up.

### Input: ###
The input consists of a record count N followed by N comma delimited rows. Each row will have 5
fields. The first field will be the word “VEST”. The second field is an arbitrary string representing an
individual employee. The third will be the **vest date** in YYYYMMDD format. The fourth is the amount of
units that are vesting. The fifth and final field is the **grant price** for those options it is a decimal number
round to two places – currency is ignored in this case. There is one final input line that consists of a date in
YYYYMMDD format and a **market price** for the stock as at that date.

### Output: ###
Output will be a two-field comma delimited row for each employee (sorted by employee
identifier). The first field will be the employee identifier. The second field will be the total cash gain
available for that employee – ignore currency and display as a decimal number rounded to 2 places. Total
gain per vested option is calculated as **market price – grant price**. Remember that different rows can have
different total gains. Any row with a negative value can be ignored. If an employee does not have anything
vested by the date given, they should still appear in the output with a total gain of **0.00**. If an employee has
a negative net gain (these options would be called “under water”), they should still appear in the output
with a total gain of **0.00**.

#### Example: ####
**Input:**

    5
    VEST,001B,20120101,1000,0.45
    VEST,002B,20120101,1500,0.45
    VEST,002B,20130101,1000,0.50
    VEST,001B,20130101,1500,0.50
    VEST,003B,20130101,1000,0.50
    20140101,1.00
**Output:**

    001B,1300.00
    002B,1325.00
    003B,500.00
If the date were changed to 20120615:

    001B,550.00
    002B,825.00
    003B,0.00

###Bonus 1:###
For exceptional performance, occasionally companies will use a multiplier against the number
of granted units as an extra bonus. For example, if an executive has her team hit certain revenue targets, she
might get a 50% bonus, for a 1.5 multiplier. So instead of 1000 units, she will receive 1500. A new record
type will be added. It is a comma delimited record where the first field is always “PERF”, the second field
is the employee identifier, the third field is the date the bonus takes effect, and the last field is the multiplier.
For simplification purposes, this multiplier *applies to all available units on or before the day of the
bonus*.

#### Example: ####
**Input:**

    5
    VEST,001B,20120102,1000,0.45
    VEST,002B,20120102,1000,0.45
    VEST,003B,20120102,1000,0.45
    PERF,001B,20130102,1.5
    PERF,002B,20130102,1.5
    20140101,1.00

**Output:**

    001B,825.00
    002B,825.00
    003B,550.00

If final date was changed to 20130101:

    001B,550.00
    002B,550.00
    003B,550.00
