import {
  Menubar,
  MenubarContent,
  MenubarItem,
  MenubarMenu,
  MenubarSeparator,
  MenubarShortcut,
  MenubarTrigger,
} from "@/components/ui/menubar"

export function NavBar() {
    return (
<div className="w-full border-b">
      <Menubar className="w-full flex justify-between px-4 py-5">
        <div className="flex items-center gap-4">
          <button className="text-lg font-bold text-primary cursor-pointer">Ticket manager</button>
          <MenubarMenu>
            <MenubarTrigger className="cursor-pointer transition-colors">Admin Panel</MenubarTrigger>
            <MenubarContent>
              <MenubarItem className="cursor-pointer">All users</MenubarItem>
              <MenubarItem className="cursor-pointer">Admins</MenubarItem>
            </MenubarContent>
          </MenubarMenu>
        </div>
        <div className="flex items-center gap-4 pr-2">
          <button className="text-sm hover:text-muted-foreground text-primary transition-colors cursor-pointer	">
            Sign In
          </button>
          <button className="text-sm font-medium text-white bg-primary px-3 py-1 rounded-md hover:bg-primary/90 transition-colors cursor-pointer">
            Sign Up
          </button>
        </div>
      </Menubar>
    </div>
    )
}