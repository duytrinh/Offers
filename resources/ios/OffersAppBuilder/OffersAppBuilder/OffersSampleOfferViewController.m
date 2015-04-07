//
//  OffersSampleOfferViewController.m
//  OffersSampleApp
//
//  Created by Leonis&Co. on 2013/10/24.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersSampleOfferViewController.h"

#import "OffersSampleCouponDetailViewController.h"
#import "SimpleTableViewCell.h"
#import "DetailsOffersViewController.h"
#import "RecommendDetailsViewController.h"
#import <OffersKit/OffersKit.h>

@interface OffersSampleOfferViewController ()
@property(nonatomic,strong) NSArray* itemList;
@end

@implementation OffersSampleOfferViewController



- (void)viewDidLoad
{
    [super viewDidLoad];

    // Remove header spaces while Back button clicked.
    self.automaticallyAdjustsScrollViewInsets = NO;
    
    /**
     **  STRING CONTENT
     **     Value   :   オススメ情報一覧    <=> Recommend list
     **
     */
    self.navigationItem.title = @"オススメ情報一覧";
    OffersKitStatusCode code;
    self.itemList = [OffersOffer offersWithStatus:&code];
    NSLog(@"StatusCode: %lu", (unsigned long)code );
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.itemList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
//    static NSString *CellIdentifier = @"Cell";
//    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
//    if( cell == nil ) {
//        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:CellIdentifier];
//    }

    static NSString *cellIdentifier = @"SimpleTableCell";
    
    SimpleTableViewCell *cell = (SimpleTableViewCell *)[tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil)
    {
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SimpleTableCell" owner:self options:nil];
        cell = [nib objectAtIndex:0];
    }
    
    // Configure the cell...
    id offerObject = [self.itemList objectAtIndex:indexPath.row];
    if([offerObject isKindOfClass:[OffersCoupon class]] == YES ) {
        OffersCoupon* coupon = (OffersCoupon*)offerObject;
        /**
         **  STRING CONTENT
         **     Value   :   クーポン    <=> Coupon
         **
         */
        cell.couponTitle.text = coupon.title;//@"クーポン";
        //cell.couponContent.text = coupon.mainDescription;
        //show text html at text view
        NSString *detailsHTMLText = [NSString stringWithFormat:@"%@\n(%@)",coupon.mainDescription, coupon.campaign];
        NSAttributedString *attributedString = [[NSAttributedString alloc] initWithData:[detailsHTMLText dataUsingEncoding:NSUnicodeStringEncoding] options:@{ NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType } documentAttributes:nil error:nil];
        cell.couponContent.attributedText = attributedString;
        
        [coupon loadImagesWithCallbackBlock:^(BOOL result) {
            dispatch_async(dispatch_get_main_queue(), ^{
                if( cell.couponImage.image == nil ) {
                    [cell.couponImage setImage:coupon.thumbnailImage];
                    [self.tbl_Offers reloadData];
                }
            });
        }];
    }
//    else {
//        OffersRecommendation* recommendation = (OffersRecommendation*)offerObject;
//        /**
//         **  STRING CONTENT
//         **     Value   :   レコメンデーション   <=> Recommend
//         **
//         */
//        cell.textLabel.text = [NSString stringWithFormat:@"レコメンデーション"];
//        cell.detailTextLabel.text = recommendation.name;
//        [recommendation loadImagesWithCallbackBlock:^(BOOL result) {
//            dispatch_async(dispatch_get_main_queue(), ^{
//                if( cell.imageView.image == nil ) {
//                    [cell.imageView setImage:recommendation.thumbnailImage];
//                    [self.tableView reloadData];
//                }
//            });
//        }];
//    }
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

//- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    id offer = [self.itemList objectAtIndex:indexPath.row];
//    if ( [offer isKindOfClass:[OffersRecommendation class]] == YES ) {
//        OffersSampleRecommendationDetailViewController *detailViewController = [[OffersSampleRecommendationDetailViewController alloc] initWithNibName:@"OffersSampleRecommendationDetailViewController" bundle:nil];
//        detailViewController.recommendation = offer;
//        [self.navigationController pushViewController:detailViewController animated:YES];
//    }
//    else {
//        OffersSampleCouponDetailViewController* viewController = [[OffersSampleCouponDetailViewController alloc] initWithNibName:NSStringFromClass([OffersSampleCouponDetailViewController class])
//                                                                                                                        bundle:[NSBundle mainBundle]
//                                                                 ];
//        viewController.coupon = offer;
//        [self.navigationController pushViewController:viewController animated:YES];
//        
//        [self.tableView deselectRowAtIndexPath:indexPath animated:YES];        
//    }
//}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [self performSegueWithIdentifier:@"detailsOffers" sender:indexPath];
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    self.title = @"Back";
    NSIndexPath *indexPath = sender;
    if ([[segue identifier] isEqual:@"detailsOffers"]) {
        OffersCoupon *coupon = (OffersCoupon *)[self.itemList objectAtIndex:indexPath.row];
        [[segue destinationViewController] setCoupon:coupon];
    }
}

@end
