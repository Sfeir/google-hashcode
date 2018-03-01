package model

type Taxi struct {
	RowPos int
	ColumnPos int
	Busy bool
	Destination *Ride
}
