package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
)

func DumbassLessDumb() []model.Course {

	courses := make([]model.Course, model.FleetSize)

	for step := 0; step < model.TotalTime; step++ {
		for idTaxi, taxi := range model.Fleet {
			if taxi.Busy <= 0 {
				heavierRide := new(model.Ride)
				heavierPoints := 0
				setted := false
				hasAvailable := false
				posRide := 0
				for i, ride := range model.Rides {
						points, available := CalculatePointsOf(step, taxi, ride)
				//		logger.Info("Ride ", ride.Id, " has ", points, "pts")
						if !setted || (points > heavierPoints && available) {
							heavierPoints = points
							heavierRide = ride
							posRide = i
							setted = true
							hasAvailable = available ||hasAvailable
						}
				}
				if hasAvailable  {
					heurePriseCourse := Distance(taxi.ColumnPos, taxi.RowPos, heavierRide.BeginColumn, heavierRide.BeginRow)
					taxi.Busy = heurePriseCourse + Distance(heavierRide.BeginColumn, heavierRide.BeginRow, heavierRide.EndColumn, heavierRide.EndRow)
					taxi.ColumnPos = heavierRide.EndColumn
					taxi.RowPos = heavierRide.EndRow
					logger.Info("Add ride ", heavierRide.Id, " to taxi ", idTaxi)
					courses = AddRideToVehicule(courses, heavierRide.Id, idTaxi)
					model.Rides = append(model.Rides[:posRide], model.Rides[posRide+1:]...)
				}
			} else {
				taxi.Busy--
			}
		}
	}

	return courses
}
