package algo
/*
import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
	"math"
)

type shape struct {
	column, row int
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
	endX := x + s.column
	endY := y + s.row
	logger.Debug("try shape : ", s, " from (", x, ",", y, ") to (", endX-1, ",", endY-1, ")")
	if (endX >= len(c[0])) || (endY >= len(c)) {
		logger.Debug("MinAlgo :Can't fit -- out of bounds")
		return false
	}

	tomatoCounter := 0
	mushroomCounter := 0
	for j := x; j < endX; j++ {
		for i := y; i < endY; i++ {
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
	for j := x; j < x+s.column; j++ {
		for i := y; i < y+s.row; i++ {
			c[i][j].Taken = true
		}
	}
	result := model.Slice{StartX: x, StartY: y, EndX: x + s.column - 1, EndY: y + s.row - 1}
	logger.Debug("Slice found :", result)
	return result
}

func MinShapesAlgo(inputs *model.Inputs) []model.Slice {
	availableShapes := calculateAvailableShapes(2 * inputs.MinNumberOfIngredient)
	result := []model.Slice{}
	for _, s := range availableShapes {
		for y := 0; y < inputs.NbRows; y++ {
			for x := 0; x < inputs.NbColumns; x++ {
				if shapeCanFit(s, inputs.Cells, x, y, inputs.MinNumberOfIngredient) {
					result = append(result, declareAllCellsTaken(s, inputs.Cells, x, y))
				}
			}
		}
	}
	return result
}
*/
