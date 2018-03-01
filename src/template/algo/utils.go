package algo

import (
	"math"

	"github.com/Sfeir/google-hashcode-lille/src/model"
)

func Distance(x1, y1, x2, y2 int) int {
	return int(math.Abs(float64(x2-x1)) + math.Abs(float64(y2-y1)))
}

func TrajetPossible(heureCourante int, taxi model.Taxi, ride model.Ride) bool {
	distTaxiRide := Distance(taxi.ColumnPos, taxi.RowPos, ride.BeginColumn, ride.BeginRow)
	distRide := Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)
	distHorraire := ride.LatestFinish - (heureCourante + distRide + distTaxiRide)
	return distHorraire >= 0
}

func TempsMaxAvantRetard(heureCourante int, ride model.Ride) int {
	distRide := Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)
	distHorraire := ride.LatestFinish - (heureCourante + distRide)
	return distHorraire
}
