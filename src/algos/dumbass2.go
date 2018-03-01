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
				heavierRide := model.Rides[0]
				heavierRideId := 0
				heavierPoints := 0
				for idRide, ride := range model.Rides {
					if !ride.Done {
						points := CalculatePointsOf(step, taxi, ride)
						if points > heavierPoints {
							heavierPoints = points
							heavierRide = ride
							heavierRideId = idRide
						}
					}
				}
				if heavierPoints > 0 {
					heurePriseCourse := Distance(taxi.ColumnPos, taxi.RowPos, heavierRide.BeginColumn, heavierRide.BeginRow)
					taxi.Busy = heurePriseCourse + Distance(heavierRide.BeginColumn, heavierRide.BeginRow, heavierRide.EndColumn, heavierRide.EndRow)
					heavierRide.Done = true
					taxi.ColumnPos = heavierRide.EndColumn
					taxi.RowPos = heavierRide.EndRow
					logger.Info("Add ride ", heavierRideId, " to taxi ", idTaxi)
					courses = AddRideToVehicule(courses, heavierRideId, idTaxi)
				}
			} else {
				taxi.Busy--
			}
		}
	}

	return courses
}
