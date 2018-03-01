package algos

import (
	"math"

	"github.com/Sfeir/google-hashcode-lille/src/model"
)

func Distance(x1, y1, x2, y2 int) int {
	return int(math.Abs(float64(x2-x1)) + math.Abs(float64(y2-y1)))
}

func TrajetPossibleJusque(heureCourante int, taxi *model.Taxi, ride *model.Ride) int {
	distTaxiRide := Distance(taxi.ColumnPos, taxi.RowPos, ride.BeginColumn, ride.BeginRow)
	distRide := Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)
	distHorraire := ride.LatestFinish - (heureCourante + distRide + distTaxiRide)
	return distHorraire
}

func CalculatePointsOf(heureCourante int, taxi *model.Taxi, ride *model.Ride) int {
	points := 0
	distTaxiRide := Distance(taxi.ColumnPos, taxi.RowPos, ride.BeginColumn, ride.BeginRow)
	distRide := Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)
	distHorraire := ride.LatestFinish - (heureCourante + distRide + distTaxiRide)
	if distHorraire < 0 {
		return -1
	}

	timeToRide := ride.EarlistStart - heureCourante

	points = distRide                                                   // Start with a points == distance
	points -= int(math.Max(float64(distTaxiRide), float64(timeToRide))) // Remove the distance to the ride
	if timeToRide > distTaxiRide {
		points += model.BonusPerRide
	}

	return points
}

func TempsMaxAvantRetard(heureCourante int, ride *model.Ride) int {
	distRide := Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)
	distHorraire := ride.LatestFinish - (heureCourante + distRide)
	return distHorraire
}

func AddRideToVehicule(c []model.Course, r int, v int) []model.Course {
	c[v].Rides = append(c[v].Rides, r)
	return c
}

func TempsIndispoTaxi(heureCourante int, taxi *model.Taxi, ride *model.Ride) int {
	distTaxiRide := Distance(taxi.ColumnPos, taxi.RowPos, ride.BeginColumn, ride.BeginRow)
	distRide := Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)

	tempsTrajetAvant := distTaxiRide

	if distTaxiRide+heureCourante < ride.EarlistStart {
		tempsTrajetAvant = ride.EarlistStart - heureCourante
	}

	return tempsTrajetAvant + distRide
}
