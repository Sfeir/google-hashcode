package model

type Taxi struct {
	RowPos      int
	ColumnPos   int
	Busy        int
	Destination *Ride
}
