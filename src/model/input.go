package model

type Inputs struct {
	NbColumns             int
	NbRows                int
	MinNumberOfIngredient int
	MaxSizeSlice          int
	Cells                 [][]Cell
}
