package model

var (
	NbColumns int
	NbRows    int
)

type Inputs struct {
	MinNumberOfIngredient, MaxSizeSlice int
	Cells                               [][]Cell
}
