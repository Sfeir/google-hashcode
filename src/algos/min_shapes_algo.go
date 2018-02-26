package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
	"math"
)

type shape struct {
	row, column int
}

func calculateAvailableShapes(size int) []shape {
	var result = []shape{}
	result = append(result, shape{row: 1, column: size})
	result = append(result, shape{row: size, column: 1})

	ceil := int(math.Ceil(float64(size) / 2))
	floor := int(math.Floor(float64(size) / 2))
	result = append(result, shape{ceil, floor})
	if ceil != floor {
		result = append(result, shape{floor, ceil})
	}
	return result
}

func shapeCanFit(s shape, c [][]model.Cell, x int, y int, eltCount int) bool {
	logger.Debug("try shape : ", s, " from ", x, " to ", y)
	if (x+s.column > len(c[0])) || (y+s.row > len(c)) {
		logger.Debug("MinAlgo :Can't fit -- out of bounds")
		return false
	}

	tomatoCounter := 0
	mushroomCounter := 0
	for i := y; i < y+s.row; i++ {
		for j := x; j < x+s.column; j++ {
			if c[i][j].Tomato {
				tomatoCounter++
			} else {
				mushroomCounter++
			}
			if true == c[i][j].Taken {
				logger.Debug("MinAlgo :Can't fit")
				return false
			}
		}
	}
	logger.Debug("MinAlgo : tomato(", tomatoCounter, ") mush(", mushroomCounter, ") for min of ", eltCount)
	return tomatoCounter >= eltCount && mushroomCounter >= eltCount
}

func declareAllCellsTaken(s shape, c [][]model.Cell, x int, y int) model.Slice {
	for i := y; i < y+s.row; i++ {
		for j := x; j < x+s.column; j++ {
			logger.Debug("Taken : ", c[i][j])
			c[i][j].Taken = true
		}
	}
	result := model.Slice{StartX: x, StartY: y, EndX: x + s.column, EndY: y + s.row}
	logger.Debug("Slice found :", result)
	return result
}

func MinShapesAlgo(inputs *model.Inputs) []model.Slice {
	availableShapes := calculateAvailableShapes(inputs.MaxSizeSlice)
	result := []model.Slice{}
	for x := 0; x < inputs.NbRows; x++ {
		for y := 0; y < inputs.NbColumns; y++ {
			for _, s := range availableShapes {
				if shapeCanFit(s, inputs.Cells, x, y, inputs.MinNumberOfIngredient) {
					result = append(result, declareAllCellsTaken(s, inputs.Cells, x, y))
				}
			}
		}
	}
	return result
}
