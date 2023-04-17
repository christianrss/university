package vm;

public class Bytecode
{
	public static class Instruction
	{
		String name; // E.g., "iadd", "call"
		int nOperands = 0;
		public Instruction(String name)
		{
			this(name,0);
		}
		
		public Instruction(String name, int nargs)
		{
			this.name 	   = name;
			this.nOperands = nargs;
		}
	}
	// Instruction BYTECODES (byte is signed; use a short to keep)
	public static final short IADD   = 1; // int add
	public static final short ISUB   = 2; 
	public static final short IMUL   = 3;
	public static final short ILT    = 4; // int less than
	public static final short IEQ    = 5; // int equal
	public static final short BR     = 6; // branch
	public static final short BRT    = 7; // branch if true
	public static final short BRF    = 8; // branch if false
	public static final short ICONST = 9; // push constant integer
	public static final short LOAD   = 10; // load from local
	public static final short GLOAD  = 11; // load from global
	public static final short STORE  = 12; // store in local
	public static final short GSTORE = 13; // store in global memory
	public static final short PRINT  = 14; // print stack top
	public static final short POP    = 15; // throw away top
	public static final short HALT   = 16;
	
	public static Instruction[] instructions = new Instruction[] {
			null, // <INVALID>
			new Instruction("IADD"), // index is the opcode
			new Instruction("ISUB"),
			new Instruction("IMUL"),
			new Instruction("ILT"),
			new Instruction("IEQ"),
			new Instruction("BR",     1),
			new Instruction("BRT",    1),
			new Instruction("BRF",    1),
			new Instruction("ICONST", 1),
			new Instruction("LOAD",   1),
			new Instruction("GLOAD",  1),
			new Instruction("STORE",  1),
			new Instruction("GSTORE", 1),
			new Instruction("PRINT"),
			new Instruction("POP"),
			new Instruction("HALT")
	};
}
