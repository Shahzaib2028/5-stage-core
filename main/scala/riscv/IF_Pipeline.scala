package riscv

import chisel3._

class IF_Pipeline extends Module{
	val io = IO(new Bundle{

		val ins_Input = Input(UInt(32.W))
		val pc4_Input = Input(UInt(32.W))
		val pc_Input  = Input(UInt(32.W))

		val ins_Output = Output(UInt(32.W))
		val pc4_Output = Output(UInt(32.W))
		val pc_Output = Ouptput(UInt(32.W))

})
		val reg_pc = RegInit(0.U(32.W))
		val reg_pc4 = RegInit(0.U(32.W))
		val reg_ins = RegInit(0.U(32.W))

		reg_pc := io.pc_Input
		reg_pc4 := io.pc4_Input
		reg_ins := io.ins_Input

		io.pc_Output := reg_pc
		io.pc4_Output := reg_pc4
		io.ins_Output := reg_ins

}
